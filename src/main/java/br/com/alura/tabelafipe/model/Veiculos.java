package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculos(
        @JsonAlias("Valor") String valor,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") String anoModelo,
        @JsonAlias("Combustivel") String combustivel

) {

    @Override
    public String toString() {
        return String.format("%s %s  ano: %s valor: %s combustível: %s",
                marca, modelo, anoModelo, valor, combustivel);
    }

}
