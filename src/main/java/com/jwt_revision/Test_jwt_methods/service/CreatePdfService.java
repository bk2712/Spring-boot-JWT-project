package com.jwt_revision.Test_jwt_methods.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreatePdfService {

    private static final Logger logger= LoggerFactory.getLogger(CreatePdfService.class);

    public void createPdf(){
        logger.info("Pdf Service is started: ");

        String title= "This is the tite";
        String content= "This is the content";
    }

}
