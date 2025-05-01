package com.jwt_revision.Test_jwt_methods.repository;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository {
    XWPFDocument getDocumentById(String documentId);

    void saveDocument(XWPFDocument document);
}
