package com.jwt_revision.Test_jwt_methods.controller;


import com.jwt_revision.Test_jwt_methods.service.CreatePdfService;
import jakarta.xml.bind.JAXBException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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



    @PostMapping("/find-replace-using-apache-poi")
    public ResponseEntity<byte[]> textReplaceWithApache(@RequestBody Map<String, String> request) {
        try {
            // Extract the base64 DOCX content
            String base64Docx = (String) request.remove("base64Content");

            // The rest of the body contains placeholder-value pairs
            Map<String, String> replacements = (Map) request;

            // Generate the PDF
            byte[] pdfBytes = createPdfService.replaceAndGeneratePdf(base64Docx, replacements);

            // Prepare response headers for PDF download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment().filename("output.pdf").build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("PDF generation failed: " + e.getMessage()).getBytes());
        }
    }


}
