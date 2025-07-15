package com.tallerwebi.presentacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
  @RequestMapping("/error-404")
  public String error404() {
    return "error-404";
  }
}