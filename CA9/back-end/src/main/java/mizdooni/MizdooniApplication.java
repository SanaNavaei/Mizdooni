package mizdooni;

import mizdooni.config.FilePropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MizdooniApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MizdooniApplication.class);
        app.addInitializers((ConfigurableApplicationContext applicationContext) -> {
            applicationContext.getEnvironment().getPropertySources().addLast(new FilePropertySource());
        });
        app.run(args);
    }
}
