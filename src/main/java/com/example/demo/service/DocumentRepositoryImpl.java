package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class DocumentRepositoryImpl implements DocumentRepository {
    @Override
    public List<Document> findAll() {
        return null;
    }

    @Override
    public List<Document> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Document document) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Document> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public <S extends Document> long count(Example<S> example) {
        return 0;
    }
}
