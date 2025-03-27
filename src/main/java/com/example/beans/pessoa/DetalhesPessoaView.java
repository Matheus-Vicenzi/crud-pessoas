package com.example.beans.pessoa;

import com.example.enums.Sexo;
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
import java.util.List;
import java.util.Map;

@Named("DetalhesPessoaView")
@ViewScoped
public class DetalhesPessoaView implements Serializable {

    @Inject
    private PessoaService pessoaService;

    private Long pessoaId;

    private Pessoa pessoa;
    private List<Sexo> listaSexo;

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

    @PostConstruct
    public void init() {
        setListaSexo(List.of(Sexo.values()));
        setPessoaOnInitialization();
    }

    public void salvarAlteracoes() {
        try {
            pessoa.setIdade(DateUtils.getYearsSince(pessoa.getDataNascimento()));
            pessoaService.salvar(pessoa);
            setPessoa(pessoaService.buscarPorId(pessoa.getId()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Alterações salvas com sucesso!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Erro ao salvar alterações.", null));
            e.printStackTrace();
        }
    }

    private void setPessoaOnInitialization() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

        String idParam = params.get("id");
        if (idParam == null) return;

        pessoaId = Long.parseLong(idParam);
        pessoa = pessoaService.buscarPorId(pessoaId);
    }
}
