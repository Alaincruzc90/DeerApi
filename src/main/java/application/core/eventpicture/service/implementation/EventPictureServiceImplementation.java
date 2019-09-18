package application.core.eventpicture.service.implementation;

import application.core.eventpicture.dao.EventPictureDao;
import application.core.eventpicture.service.EventPictureService;
import application.model.EventPicture;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@PropertySource("classpath:properties/image_location.properties")
public class EventPictureServiceImplementation implements EventPictureService {

    @Value("${base.location}")
    private String BASE_LOCATION;

    @Value("${event.location}")
    private String EVENT_LOCATION;

    @Autowired
    EventPictureDao eventPictureDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public EventPicture findById(Long id) {
        return this.eventPictureDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(EventPicture eventPicture) throws Exception {
        this.eventPictureDao.persist(eventPicture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(EventPicture eventPicture) throws Exception {
        this.eventPictureDao.delete(eventPicture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(EventPicture eventPicture) throws Exception {
        this.eventPictureDao.update(eventPicture);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventPicture> getAllByEventId(Long eventId) {
        return this.eventPictureDao.getAllByEventId(eventId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertEventPictures(Long eventId, List<MultipartFile> images) throws Exception {
        for (MultipartFile image : images) {
            this.insertEventPicturesAux(eventId, image);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEventPictures(Long eventId) throws Exception {

        // First fetch all the corresponding pictures to that id.
        List<EventPicture> eventPictures = this.getAllByEventId(eventId);

        // If there is nothing to delete, just continue.
        if (eventPictures == null) return;

        // Delete all the event pictures.
        for (EventPicture e: eventPictures) {
            this.delete(e);
            this.deleteImages(e);
        }

    }

    /**
     * Insert a new image.
     *
     * @param eventId Event's id.
     * @param image   Image that will be inserted.
     * @throws Exception Error with any of the IO operations.
     */
    private void insertEventPicturesAux(Long eventId, MultipartFile image) throws Exception {

        // First, let's try to add the new event picture entry in our database.
        EventPicture eventPicture = new EventPicture();
        eventPicture.setEventId(eventId);
        eventPicture.setName(eventId.toString());
        eventPicture.setPathSmall("");
        eventPicture.setPathMedium("");
        eventPicture.setPathBig("");
        eventPicture.setDateCreated(new Date());
        this.persist(eventPicture);

        // Base of our picture url.
        final String baseName = eventId + "_" + eventPicture.getPictureId();

        // Modify the event picture, then update it.
        eventPicture.setName(baseName);
        eventPicture.setPathSmall(baseName + "@small.jpg");
        eventPicture.setPathMedium(baseName + "@medium.jpg");
        eventPicture.setPathBig(baseName + "@big.jpg");
        this.update(eventPicture);

        // Now, let's try to create the image.
        try {
            this.insertImages(eventId, eventPicture.getPictureId(), image.getContentType(), image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Insert an image in our server.
     *
     * @param eventId   Id of the event to which the image correspond.
     * @param pictureId Id of the picture.
     * @param type      Type of the picture.
     * @param file      Image that will be added.
     * @throws Exception Error with any of the IO operations.
     */
    private void insertImages(Long eventId, Long pictureId, String type, MultipartFile file) throws Exception {

        // Base folder, where our franchise images are uploaded to.
        File baseFolder = new File(BASE_LOCATION + EVENT_LOCATION + "/" + eventId);

        // If folder doesn't exist, then create it.
        if (!baseFolder.exists()) {
            if (!baseFolder.mkdirs()) {
                throw new IOException("No se pudo crear el directorio padre.");
            }
        }

        // Read image from file.
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

        // Check if the image is wider or taller.
        boolean wider = src.getWidth() >= src.getHeight();
        if (wider) {
            if (src.getHeight() < 500)
                throw new IOException("La imagen no tiene la calidad necesaria..");
        } else {
            if (src.getWidth() < 500)
                throw new IOException("La imagen no tiene la calidad necesaria.");
        }

        // We need to create three versions of the same image. A main version at a 512px width
        // one at 1024px and a third one at 256px width. For each one of them, we need to create a file.
        // We are identifying each image by using the termination of @main, @big and @thumbnail.
        File mainImage = new File(baseFolder.getPath() + "/" + eventId + "_" + pictureId + "@medium.jpg");
        File thumbnailImage = new File(baseFolder.getPath() + "/" + eventId + "_" + pictureId + "@small.jpg");
        File bigImage = new File(baseFolder.getPath() + "/" + eventId + "_" + pictureId + "@big.jpg");
        BufferedImage newMainBufferedImage;
        BufferedImage newThumbnailImage;
        BufferedImage newBigImage;

        // Constants with the size of each image needed to be created for each logo.
        final int THUMBNAIL_LOGO_SIZE = 256;
        final int MAIN_LOGO_SIZE = 512;
        final int BIG_LOGO_SIZE = 1024;

        // We need to check if the image is a png.
        BufferedImage newSrc = new BufferedImage(src.getWidth(),
                src.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Add white background to the image.
        newSrc.createGraphics().drawImage(src, 0, 0, Color.WHITE, null);
        try {
            if (type.equals("image/png")) {
                newMainBufferedImage = scale(newSrc, MAIN_LOGO_SIZE);
                newThumbnailImage = scale(newSrc, THUMBNAIL_LOGO_SIZE);
                newBigImage = scale(newSrc, BIG_LOGO_SIZE);
            } else {
                newMainBufferedImage = scale(newSrc, MAIN_LOGO_SIZE);
                newThumbnailImage = scale(newSrc, THUMBNAIL_LOGO_SIZE);
                newBigImage = scale(newSrc, BIG_LOGO_SIZE);
            }
        } catch (Exception e) {
            throw new IOException("No se pudo redimensionar la imagen.");
        }
        try {

            // Now, we try to write on the server.
            ImageIO.write(newMainBufferedImage, "jpg", mainImage);
            ImageIO.write(newThumbnailImage, "jpg", thumbnailImage);
            ImageIO.write(newBigImage, "jpg", bigImage);

        } catch (Exception e) {
            throw new IOException("No pudimos escribir la imagen en el servidor.");
        }
    }

    /**
     * Delete all the corresponding configurations of an event picture.
     *
     * @param eventPicture Event picture that we would like to delete.
     * @throws IOException Error deleting the images.
     */
    private void deleteImages(EventPicture eventPicture) throws IOException {

        // Base folder where our images could be located.
        File baseFolder = new File(BASE_LOCATION + EVENT_LOCATION + "/" + eventPicture.getEventId());

        // Check if the folder exists, if not, then there is nothing to delete.
        if (!baseFolder.exists()) {
            throw new IOException("No se pudo eliminar las imagenes, pues la carpeta padre no existe.");
        }

        // Delete the three images of the product media.
        Files.deleteIfExists(Paths.get(baseFolder.getPath() + "/" + eventPicture.getPathSmall()));
        Files.deleteIfExists(Paths.get(baseFolder.getPath() + "/" + eventPicture.getPathMedium()));
        Files.deleteIfExists(Paths.get(baseFolder.getPath() + "/" + eventPicture.getPathBig()));
    }

    /**
     * Scales images to a given width.
     *
     * @param img   Image that will be scaled.
     * @param width New width of the scaled image.
     * @return New resized image.
     * @throws Exception IOException if we can't modify the image.
     */
    private BufferedImage scale(BufferedImage img, int width) throws Exception {
        double height = ((double) width / img.getWidth()) * img.getHeight();
        return Thumbnails.of(img).size(width, (int) height).asBufferedImage();
    }
}
