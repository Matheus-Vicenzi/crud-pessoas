package com.example.dto.pessoa;

import com.example.dto.endereco.EnderecoDTO;

import java.util.List;

public class PessoaDTO {

    private String nome;
    private String dataNascimento;
    private Integer idade;
    private String sexo;
    private List<EnderecoDTO> enderecos;

    public PessoaDTO() {
    }

    public PessoaDTO(String nome, String dataNascimento, Integer idade, String sexo, List<EnderecoDTO> enderecos) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.sexo = sexo;
        this.enderecos = enderecos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
