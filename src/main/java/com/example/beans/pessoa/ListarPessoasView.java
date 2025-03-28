package com.example.beans.pessoa;

import com.example.model.pessoa.Pessoa;
import com.example.service.pessoa.PessoaService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("ListarPessoasView")
public class ListarPessoasView implements Serializable {

    @Inject
    private PessoaService pessoaService;

    private List<Pessoa> pessoas;
    private Long pessoaIdExclusao;

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public Long getPessoaIdExclusao() {
        return pessoaIdExclusao;
    }

    @PostConstruct
    public void init() {
        pessoas = pessoaService.listar();
    }

    public void remover(Long pessoaId) {
        pessoaService.remover(pessoaId);
        pessoas = pessoaService.listar();
        pessoaIdExclusao = null;
    }

    public void visualizarDetalhes(Long pessoaId) {
        Pessoa pessoa = pessoaService.buscarPorId(pessoaId);
        System.out.println(pessoa);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("detalhes-pessoa.xhtml?faces-redirect=true&id=" + pessoaId);
        } catch (IOException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao acessar consulta detalhada de pessoas", null));
        }
    }

    public void iniciarExclusao(Long pessoaId) {
        pessoaIdExclusao = pessoaId;
        PrimeFaces.current().executeScript("PF('confirmaExclusao').show()");
    }

}
