package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CustomDocumentRepositoryImpl implements CustomDocumentRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Document> findByCategory(String documentCategory) {
        Query query = entityManager.createNativeQuery("SELECT * FROM documents as em " +
                "WHERE DOCUMENT_CATEGORY LIKE ? ", Document.class);
        query.setParameter(1, documentCategory + "%");
        return query.getResultList();
    }
}
