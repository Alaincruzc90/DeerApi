package application.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@CrossOrigin
@PropertySource("classpath:properties/image_location.properties")
public class ImageController {

    @Value("${base.location}")
    private String BASE_LOCATION;

    @Value("${event.location}")
    private String EVENT_LOCATION;

    // Returns a byte array of an image from the server.
    @RequestMapping(value = "/event/{eventId}/{imageName}")
    @ResponseBody
    public byte[] getEventImages(@PathVariable("eventId") Long eventId,
                                 @PathVariable(value = "imageName") String imageName) throws IOException {

        // Best practice, add variable paths in the properties file. That way it is easier to manage the paths.
        // When deployed to a server, remember to change the base path.
        File baseFolder = new File(BASE_LOCATION + EVENT_LOCATION + "/" + eventId);

        // Path to the desired image
        File imagePath = new File(baseFolder.getPath()+ "/" +imageName);

        // If the image doesn't exists, then we have to use a default image as replacement.
        if(!imagePath.exists()) {
            return null;
        }
        return Files.readAllBytes(imagePath.toPath());
    }
}
