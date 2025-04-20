package com.jwt_revision.Test_jwt_methods.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @PostMapping("/create-pdf")
    public void createPdf(){

    }
}
