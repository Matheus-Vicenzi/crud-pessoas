package com.example.beans.pessoa;

import com.example.enums.Sexo;
import com.example.exception.BusinessException;
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
        inicializar();
    }

    public void salvar() {
        try {
            validarCampos(pessoa);

            pessoa.setIdade(DateUtils.getYearsSince(pessoa.getDataNascimento()));

            for (Endereco endereco : listaEnderecos) {
                pessoaService.adicionarEndereco(pessoa, endereco);
            }

            pessoaService.salvar(pessoa);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Pessoa cadastrada com sucesso!", null));

            limparFormulario();
        } catch (BusinessException businessException) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar pessoa", businessException.getMessage()));
        } catch (Exception exception) {
            exception.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar pessoa", "Erro desconhecido."));
        }
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

    private void inicializar() {
        pessoa = new Pessoa();
        listaSexo = List.of(Sexo.values());
        listaEnderecos = new ArrayList<>();
    }

    private void limparFormulario() {
        pessoa = new Pessoa();
        listaEnderecos = new ArrayList<>();
    }

    private void validarCampos(Pessoa pessoa) {
        Boolean hasError = false;

        StringBuilder erros = new StringBuilder();
        erros.append("Campos inválidos:\n");

        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            hasError = true;
            erros.append("- Nome: Campo obrigatorio\n");
        }

        if (pessoa.getDataNascimento() == null) {
            hasError = true;
            erros.append("- Data de nascimento: Campo obrigatorio\n");
        }

        if (pessoa.getSexo() == null) {
            hasError = true;
            erros.append("- Sexo: Campo obrigatorio");
        }

        if (hasError) throw new BusinessException(erros.toString());
    }
}
