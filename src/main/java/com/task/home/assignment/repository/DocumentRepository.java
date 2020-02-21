package com.task.home.assignment.repository;


import com.task.home.assignment.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>, CustomDocumentRepository {
}
