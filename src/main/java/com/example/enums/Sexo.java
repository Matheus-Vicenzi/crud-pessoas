package com.example.enums;

public enum Sexo {
    MASCULINO("M"),
    FEMININO("F");

    private final String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public Boolean isMasculino() {
        return this == MASCULINO;
    }

    public Boolean isFeminino() {
        return this == FEMININO;
    }
}
