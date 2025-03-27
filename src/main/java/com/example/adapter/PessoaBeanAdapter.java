package com.example.adapter;

import com.example.enums.Sexo;
import com.example.util.DateUtils;

import java.util.Date;

public class PessoaBeanAdapter {

    private String nome;
    private Date dataNascimento;
    private Integer idade;
    private Sexo sexo;

    public PessoaBeanAdapter() {}

    public PessoaBeanAdapter(String nome, Date dataNascimento, Sexo sexo) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = DateUtils.getYearsSince(dataNascimento);
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
}
