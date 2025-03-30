package com.example.beans.pessoa;

import com.example.adapter.endereco.EnderecoCepAdapter;
import com.example.enums.Sexo;
import com.example.exception.BusinessException;
import com.example.model.endereco.Endereco;
import com.example.model.pessoa.Pessoa;
import com.example.service.endereco.EnderecoService;
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
import java.util.Map;

@Named("DetalhesPessoaView")
@ViewScoped
public class DetalhesPessoaView implements Serializable {

    @Inject
    private PessoaService pessoaService;

    @Inject
    private EnderecoService enderecoService;

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
            validarCampos(pessoa);

            pessoa.setIdade(DateUtils.getYearsSince(pessoa.getDataNascimento()));
            pessoaService.salvar(pessoa);
            setPessoa(pessoaService.buscarPorId(pessoa.getId()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Alterações salvas com sucesso!", null));
        } catch (BusinessException businessException) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar alterações.", businessException.getMessage()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Erro ao salvar alterações.", "Erro desconhecido."));
            e.printStackTrace();
        }
    }

    public void adicionarEndereco() {
        if (pessoa.getEnderecos() == null) {
            pessoa.setEnderecos(new ArrayList<>());
        }
        Endereco endereco = new Endereco();
        pessoa.addEndereco(endereco);
    }

    public void removerEndereco(Endereco endereco) {
        pessoaService.removerEndereco(pessoa, endereco);
    }

    public void buscarEnderecoPorCep(Endereco endereco) {
        try {
            if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "CEP inválido", "Por favor, informe um CEP válido."));
                return;
            }

            EnderecoCepAdapter enderecoCepAdapter = enderecoService.consultarCep(endereco.getCep());

            endereco.setEstado(enderecoCepAdapter.getEstado());
            endereco.setCidade(enderecoCepAdapter.getCidade());
            endereco.setBairro(enderecoCepAdapter.getBairro());
            endereco.setLogradouro(enderecoCepAdapter.getLogradouro());

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Endereço encontrado!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro ao buscar endereço", e.getMessage()));
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

        for (Endereco endereco : pessoa.getEnderecos()) {
            if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
                hasError = true;
                erros.append("- CEP: Campo obrigatorio");
            }

            if (endereco.getCidade() == null || endereco.getCidade().isEmpty()) {
                hasError = true;
                erros.append("- Cidade: Campo obrigatorio");
            }

            if (endereco.getEstado() == null || endereco.getEstado().isEmpty()) {
                hasError = true;
                erros.append("- Estado: Campo obrigatorio");
            }

            if (endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty()) {
                hasError = true;
                erros.append("- Logradouro: Campo obrigatorio");
            }
        }

        if (hasError) throw new BusinessException(erros.toString());
    }
}
