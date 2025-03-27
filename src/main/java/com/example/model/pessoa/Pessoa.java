package com.example.model.pessoa;

import com.example.adapter.PessoaBeanAdapter;
import com.example.enums.Sexo;
import com.example.model.BaseEntity;
import com.example.model.endereco.Endereco;
import com.example.util.DateUtils;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "pessoa")
public class Pessoa extends BaseEntity {

    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

    @Column(name = "idade", nullable = false)
    private Integer idade;

    @Column(name = "sexo", length = 2)
    private Sexo sexo;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Endereco> enderecos = new ArrayList<>();

    public Pessoa() {}

    public Pessoa(String nome, Date dataNascimento, Sexo sexo) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.idade = DateUtils.getYearsSince(dataNascimento);
    }

    public Pessoa(PessoaBeanAdapter pessoaBeanAdapter) {
        this.nome = pessoaBeanAdapter.getNome();
        this.dataNascimento = pessoaBeanAdapter.getDataNascimento();
        this.idade = pessoaBeanAdapter.getIdade();
        this.sexo = pessoaBeanAdapter.getSexo();
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public void addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
    }

    public void removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
    }

    public Endereco getEndereco(Long id) {
        if (enderecos.isEmpty() || enderecos == null)

        for (Endereco endereco : enderecos) {
            if (Objects.equals(endereco.getId(), id)) return endereco;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
