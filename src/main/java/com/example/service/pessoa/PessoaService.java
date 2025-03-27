package com.example.service.pessoa;

import com.example.exception.BusinessException;
import com.example.model.endereco.Endereco;
import com.example.model.pessoa.Pessoa;

import com.example.repository.pessoa.PessoaRepository;
import com.example.util.DateUtils;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class PessoaService {

    @Inject
    private PessoaRepository pessoaRepository;

    public void salvar(Pessoa pessoa) {
        if (isDataNascimentoValid(pessoa.getDataNascimento())) {
            throw new BusinessException("Data de nascimento superior a data atual");
        }
        pessoaRepository.salvar(pessoa);
    }

    public void remover(Long id) {
        pessoaRepository.remover(id);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.buscarPorId(id);
    }

    public List<Pessoa> listar() {
        return pessoaRepository.listar();
    }

    public void adicionarEndereco(Pessoa pessoa, Endereco endereco) {
        pessoa.addEndereco(endereco);
        endereco.setPessoa(pessoa);
    }

    public void removerEndereco(Pessoa pessoa, Endereco endereco) {
        pessoa.removeEndereco(endereco);
    }

    private Boolean isDataNascimentoValid(Date data) {
        if (data == null) return false;

        LocalDate now = LocalDate.now();
        LocalDate localDate = DateUtils.dateToLocalDate(data);

        return localDate.isAfter(now);
    }
}
