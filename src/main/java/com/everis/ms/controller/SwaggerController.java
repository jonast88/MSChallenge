package com.everis.ms.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

@Api(tags = "Swagger Redirect")
public class SwaggerController
{
    @RequestMapping(value = "/swagger", method = RequestMethod.GET)
    public String swaggerHome()
    {
        return "redirect:/swagger-ui/";
    }
}
