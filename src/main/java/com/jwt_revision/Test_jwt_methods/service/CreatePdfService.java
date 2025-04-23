package com.jwt_revision.Test_jwt_methods.service;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.docx4j.Docx4J;
import org.docx4j.wml.Text;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

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



    // this function will replace and find operation on word doc and return the pdf

    public ByteArrayInputStream findAndReplaceOpInDoc(Map<String, String> replacements) throws Exception {
        InputStream templateStream = new ClassPathResource("templates/ResidentEvil4.docx").getInputStream();
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        // This replaces all ${} variables properly, even across split runs
        documentPart.variableReplace(replacements);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        Docx4J.toPDF(wordMLPackage, pdfOutputStream);
        return new ByteArrayInputStream(pdfOutputStream.toByteArray());
    }

}
