package com.jwt_revision.Test_jwt_methods.controller;


import com.jwt_revision.Test_jwt_methods.service.CreatePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    CreatePdfService createPdfService;

    @GetMapping("/create-pdf")
    public ResponseEntity<InputStreamResource> createPdf(){
        ByteArrayInputStream pdf= createPdfService.createPdf();

        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline;file=lcwd.pdf");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
