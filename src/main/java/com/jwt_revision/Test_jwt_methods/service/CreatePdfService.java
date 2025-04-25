package com.jwt_revision.Test_jwt_methods.service;


import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.docx4j.Docx4J;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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

//    public ByteArrayInputStream findAndReplaceOpInDoc(Map<String, String> replacements) throws Exception {
//        InputStream templateStream = new ClassPathResource("templates/ResidentEvil4.docx").getInputStream();
//        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateStream);
//        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
//
//        // This replaces all ${} variables properly, even across split runs
//        documentPart.variableReplace(replacements);
//
//        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
//        Docx4J.toPDF(wordMLPackage, pdfOutputStream);
//        return new ByteArrayInputStream(pdfOutputStream.toByteArray());
//    }



    public ByteArrayInputStream findAndReplaceOpInDoc(Map<String, String> replacements) throws Exception {
        InputStream templateStream = new ClassPathResource("templates/ResidentEvil4.docx").getInputStream();
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        // Split replacements: image vs text
        Map<String, String> textReplacements = new HashMap<>();
        Map<String, byte[]> imageReplacements = new HashMap<>();

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (isBase64Image(value)) {
                imageReplacements.put("${" + key + "}", Base64.getDecoder().decode(value));
            } else {
                textReplacements.put("${" + key + "}", value);
            }
        }

        // Replace images at specific placeholders
        for (Map.Entry<String, byte[]> entry : imageReplacements.entrySet()) {
            replaceImagePlaceholderFromBytes(documentPart, entry.getKey(), entry.getValue());
        }

        // Replace remaining text
        documentPart.variableReplace(textReplacements);

        // Export to PDF
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        Docx4J.toPDF(wordMLPackage, pdfOutputStream);
        return new ByteArrayInputStream(pdfOutputStream.toByteArray());
    }


    private boolean isBase64Image(String base64) {
        try {
            byte[] decoded = Base64.getDecoder().decode(base64);
            return decoded.length > 100 && (decoded[0] == (byte) 0xFF || decoded[1] == (byte) 0xD8); // rough check for JPEG/PNG
        } catch (Exception e) {
            return false;
        }
    }

    private void replaceImagePlaceholderFromBytes(MainDocumentPart docPart, String placeholder, byte[] imageBytes) throws Exception {
        List<Object> paragraphs = getAllElements(docPart, P.class);

        for (Object paragraphObj : paragraphs) {
            P paragraph = (P) paragraphObj;
            List<Object> texts = getAllElements(paragraph, Text.class);

            for (Object textObj : texts) {
                Text text = (Text) textObj;

                if (text.getValue().contains(placeholder)) {
                    BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart((WordprocessingMLPackage) docPart.getPackage(), imageBytes);

                    Inline inline = imagePart.createImageInline("Image", "Image", 1, 2, 4000, 2500, false);

                    paragraph.getContent().clear();

                    Drawing drawing = new org.docx4j.wml.ObjectFactory().createDrawing();
                    drawing.getAnchorOrInline().add(inline);

                    R imageRun = new org.docx4j.wml.ObjectFactory().createR();
                    imageRun.getContent().add(drawing);

                    paragraph.getContent().add(imageRun);
                    return;
                }
            }
        }
    }

    private List<Object> getAllElements(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<>();

        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }

        if (toSearch.isAssignableFrom(obj.getClass())) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElements(child, toSearch));
            }
        }

        return result;
    }

}
