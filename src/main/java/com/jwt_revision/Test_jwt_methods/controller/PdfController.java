package com.jwt_revision.Test_jwt_methods.controller;


import com.jwt_revision.Test_jwt_methods.service.CreatePdfService;
import jakarta.xml.bind.JAXBException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

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

    @PostMapping("/find-replace-text")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Map<String, String> replacements) {
        try {
            ByteArrayInputStream pdfStream = createPdfService.findAndReplaceOpInDoc(replacements);

            byte[] pdfBytes = pdfStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("RacoonCityReport.pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Docx4JException | IOException | JAXBException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
