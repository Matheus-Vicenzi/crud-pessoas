package com.example.enums.pessoa;

public enum Sexo {
    MASCULINO("M"),
    FEMININO("F");

    private final String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
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

    public static Sexo fromString(String descricao) {
        if (descricao != null) {
            for (Sexo sexo : Sexo.values()) {
                if (descricao.equalsIgnoreCase(sexo.getDescricao())) {
                    return sexo;
                }
            }
        }
        throw new IllegalArgumentException("Sexo inválido. Valores permitidos: M, F.");
    }
}
