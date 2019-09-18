package application.conf;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    // Used to deserialize and serialized the geometry objects.
    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }

}
