package com.everis.ms.config;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@EnableSwagger2
public class Swagger2Config
{
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                              .basePackage("com.everis.ms.controller"))
                .paths(ant("/MSTemperature/**"))
                .build()
                .apiInfo(apiEndPointsInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getCustomizedResponseMessages())
                ;
    }

    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder()
                .title("DOCUMENTACION DE PROYECTO CHALLENGE")
                .description("Microservicio de temperaturas. ")
//                .contact(new Contact("Ramesh Fadatare", "www.javaguides.net", "ramesh24fadatare@gmail.com"))
//                .license("Apache 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

    private List<ResponseMessage> getCustomizedResponseMessages()
    {
        List<ResponseMessage> responseMessages = new ArrayList<>();
//        responseMessages.add(new ResponseMessageBuilder().code(500).message("Server has crashed!!").responseModel(
//                new ModelRef("Error")).build());
        responseMessages.add(new ResponseMessageBuilder().code(200)
                                     .message("Successfully").build());
        responseMessages.add(new ResponseMessageBuilder().code(400)
                                     .message("Se ha producido un error en temperaturas.").build());
        responseMessages.add(new ResponseMessageBuilder().code(404)
                                     .message("Los criterios de busqueda no han producido ningún resultado.").build());
        responseMessages.add(new ResponseMessageBuilder().code(500)
                                     .message("Se ha producido un error desconocido.").build());
        responseMessages.add(new ResponseMessageBuilder().code(503)
                                     .message("Servicio no disponible.").build());
        return responseMessages;
    }
}


 */