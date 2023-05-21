package com.walterpaulo.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walterpaulo.produto.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
