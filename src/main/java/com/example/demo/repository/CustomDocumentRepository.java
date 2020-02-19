package com.example.demo.repository;

import com.example.demo.model.Document;

import java.util.List;

public interface CustomDocumentRepository {
    List<Document> findByCategory(String documentCategory);
}
