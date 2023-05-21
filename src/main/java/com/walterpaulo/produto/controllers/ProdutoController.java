package com.walterpaulo.produto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walterpaulo.produto.models.Produto;
import com.walterpaulo.produto.services.ProdutoService;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Response<Produto>> novo(@RequestBody Produto produto) {
        Response<Produto> produtoObjeto = new Response<Produto>();
        produtoObjeto.setContent(service.save(produto));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(produtoObjeto);
    }

    @GetMapping
    public ResponseEntity<Response<List<Produto>>> produtos() {
        Response<List<Produto>> produtos = new Response();
        produtos.setContent(service.getAll());
        produtos.setStatus(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(produtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<Produto>> findById(@PathVariable("id") Long id) {
        Response<Produto> produtoObjeto = new Response<Produto>();
        produtoObjeto.setContent(service.findById(id).get());
        produtoObjeto.setStatus(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(produtoObjeto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<Produto>> deleteById(@PathVariable("id") Long id) {
        Response<Produto> produtoObjeto = new Response<Produto>();
        service.deleteById(id);
        produtoObjeto.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(produtoObjeto);
    }

}
