package com.walterpaulo.produto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walterpaulo.produto.models.Produto;
import com.walterpaulo.produto.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;

    public Produto save(Produto produto){
        return repository.save(produto);
    }

    public List<Produto> getAll(){
        return repository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
