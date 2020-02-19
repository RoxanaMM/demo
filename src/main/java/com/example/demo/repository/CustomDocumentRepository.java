package com.example.demo.repository;

import com.example.demo.model.Document;

import java.sql.SQLException;
import java.util.List;

public interface CustomDocumentRepository {
    List<String> findByCategory(String documentCategory) throws SQLException;
}
