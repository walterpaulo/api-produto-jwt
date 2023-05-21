package com.walterpaulo.produto.controllers;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
    private int status;
    private T content;
    private List<String> erros;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @SuppressWarnings("unchecked")
    public void setContentList(List<T> content) {
        this.content = (T) content;
    }

    public List<String> getErros() {
        if(this.erros == null){
            this.erros = new ArrayList<>();
        }
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
