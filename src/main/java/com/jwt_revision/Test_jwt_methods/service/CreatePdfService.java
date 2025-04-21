package com.jwt_revision.Test_jwt_methods.service;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class CreatePdfService {

    private static final Logger logger= LoggerFactory.getLogger(CreatePdfService.class);

    public ByteArrayInputStream createPdf(){
        logger.info("Pdf Service is started: ");

        String title= "This is the tite";
        String content= "This is the content";
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        Document document= new Document();

        PdfWriter.getInstance(document, out);//document me jo bhi hmm write krenge wo iss out me write ho jaega
        document.open();

        Font titleFont= FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 25);
        Paragraph docTitle= new Paragraph(title, titleFont);
        docTitle.setAlignment(Element.ALIGN_CENTER);

        document.add(docTitle);

        Font paraFont= FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18);

        Paragraph docContent= new Paragraph(content, paraFont);
        document.add(docContent);

        document.close();

        // out ko read krne k lie inputArrayStream me convert krna pdega

        return new ByteArrayInputStream(out.toByteArray());

    }

}
