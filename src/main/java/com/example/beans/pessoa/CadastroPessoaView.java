package com.example.beans.pessoa;

import com.example.enums.Sexo;
import com.example.model.endereco.Endereco;
import com.example.model.pessoa.Pessoa;
import com.example.service.pessoa.PessoaService;
import com.example.util.DateUtils;
import jakarta.annotation.PostConstruct;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("CadastroPessoaView")
@ViewScoped
public class CadastroPessoaView implements Serializable {

    private Pessoa pessoa;
    private List<Sexo> listaSexo;
    private List<Endereco> listaEnderecos;

    @Inject
    private PessoaService pessoaService;

    @PostConstruct
    public void init() {
        pessoa = new Pessoa();
        listaSexo = List.of(Sexo.values());
        listaEnderecos = new ArrayList<>();
    }

    public void salvar() {
        pessoa.setIdade(DateUtils.getYearsSince(pessoa.getDataNascimento()));

        for (Endereco endereco : listaEnderecos) {
            endereco.setPessoa(pessoa);
        }

        pessoa.setEnderecos(listaEnderecos);
        pessoaService.salvar(pessoa);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Pessoa cadastrada com sucesso!", null));

        pessoa = new Pessoa();
        listaEnderecos = new ArrayList<>();
    }

    public void adicionarEndereco() {
        listaEnderecos.add(new Endereco());
    }

    public void removerEndereco(Endereco endereco) {
        listaEnderecos.remove(endereco);
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Sexo> getListaSexo() {
        return listaSexo;
    }

    public void setListaSexo(List<Sexo> listaSexo) {
        this.listaSexo = listaSexo;
    }

    public List<Endereco> getListaEnderecos() {
        return listaEnderecos;
    }

    public void setListaEnderecos(List<Endereco> listaEnderecos) {
        this.listaEnderecos = listaEnderecos;
    }
}
