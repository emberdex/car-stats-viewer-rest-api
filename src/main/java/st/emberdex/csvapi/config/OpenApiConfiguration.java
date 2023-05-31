package st.emberdex.csvapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI openApi() {

    return new OpenAPI()
        .info(
            new Info().title("Car Stats Viewer REST API")
                .description("REST API for the Car Stats Viewer Android Automotive application")
                .version("1.0.0")
                .license(new License().name("MIT License").url("https://github.com/emberdex/car-stats-viewer-rest-api/blob/main/LICENSE"))

        );
  }
}
