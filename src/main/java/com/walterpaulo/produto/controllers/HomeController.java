package com.walterpaulo.produto.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public ResponseEntity<Response<Object>> index(){
        Response<Object> retorn =  new Response<Object>();
        retorn.setStatus(HttpStatus.OK.value());;
        retorn.setContent("API - Produtos");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(retorn);
    }
}
