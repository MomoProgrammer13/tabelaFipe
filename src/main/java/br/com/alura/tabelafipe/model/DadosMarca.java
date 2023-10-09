package br.com.alura.tabelafipe.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record DadosMarca (@JsonAlias("codigo") String codigo, @JsonAlias("nome") String nome){

}
