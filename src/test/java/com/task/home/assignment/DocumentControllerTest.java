package com.task.home.assignment;


import com.task.home.assignment.exception.handler.CustomExceptionHandlerInternalServerError;
import com.task.home.assignment.model.Document;
import com.task.home.assignment.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@DataJpaTest
public class DocumentControllerTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    public void findDocumentList() {
        List<Document> documentsInDb = allDemoDataPresentTheFirstTimeInDb();
        List<Document> documentListFound = documentRepository.findAll();
        assert (Collections.disjoint(documentsInDb, documentListFound));
    }

    @Test
    public void findDocumentsByCategory() throws SQLException, CustomExceptionHandlerInternalServerError {
        Document document = new Document("myFile1.pdf");
        document.setDocumentMimeType("application/pdf");
        document.setDocumentCategory("unknown");
        documentRepository.saveDocument(document, "myFile1.pdf");
        documentRepository.flush();

        List<Document> documentsFound = documentRepository.findByCategory(document.getDocumentCategory());

        assert (documentsFound.stream().anyMatch(d -> d.getDocumentCategory().equals("unknown") && d.getDocumentName().equals("myFile1.pdf")));
    }

    @Test
    public void findDocumentsByName() throws SQLException, CustomExceptionHandlerInternalServerError {
        Document document = new Document("myFile.pdf");
        document.setDocumentMimeType("application/pdf");
        document.setDocumentCategory("unknown");
        documentRepository.saveDocument(document, "myFile.pdf");
        documentRepository.flush();

        Document found = documentRepository.findByName(document.getDocumentName());

        assert (found.getDocumentName())
                .equals(document.getDocumentName());
    }

    private List<Document> allDemoDataPresentTheFirstTimeInDb() {
        List<Document> documentList = new ArrayList<>();

        Document document1 = new Document(0123, "contract.pdf", "contract", "application/pdf");
        Document document2 = new Document(1234, "joy.png", "entertainment", "image/png");
        Document document3 = new Document(2345, "fun.pdf", "entertainment", "application/pdf");
        Document document4 = new Document(3456, "nvoice.pdf", "finance", "application/pdf");
        Document document5 = new Document(4567, "receipt.png", "entertainment", "image/png");
        Document document6 = new Document(5678, "unknown.txt", "unknown", "application/plain");

        return documentList;
    }
}
