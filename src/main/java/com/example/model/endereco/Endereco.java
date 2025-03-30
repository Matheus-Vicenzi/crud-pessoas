package com.example.model.endereco;

import com.example.adapter.endereco.EnderecoCepAdapter;
import com.example.model.BaseEntity;
import com.example.model.pessoa.Pessoa;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "endereco")
public class Endereco extends BaseEntity {

    @Column(name = "estado", length = 2, nullable = false)
    private String estado;

    @Column(name = "cidade", length = 100, nullable = false)
    private String cidade;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "logradouro", length = 100, nullable = false)
    private String logradouro;

    // Utilizado String como tipo pois não é feito calculo com esse valor
    // e algumas propriedades utilizam numeros + letras para referência
    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "cep", nullable = false)
    private String cep;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    @JsonbTransient
    private Pessoa pessoa;

    public Endereco() {}

    public Endereco(EnderecoCepAdapter enderecoCepAdapter, String complemento, String numero, Pessoa pessoa) {
        this.cep = enderecoCepAdapter.getCep();
        this.estado = enderecoCepAdapter.getEstado();
        this.cidade = enderecoCepAdapter.getCidade();
        this.bairro = enderecoCepAdapter.getBairro();
        this.logradouro = enderecoCepAdapter.getLogradouro();
        this.numero = numero;
        this.pessoa = pessoa;
        this.complemento = complemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
