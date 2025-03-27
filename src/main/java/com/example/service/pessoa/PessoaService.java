package com.example.service.pessoa;

import com.example.model.endereco.Endereco;
import com.example.model.pessoa.Pessoa;

import com.example.repository.pessoa.PessoaRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class PessoaService {

    @Inject
    private PessoaRepository pessoaRepository;

    public void salvar(Pessoa pessoa) {
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

    // Método para adicionar um novo endereço
    public void adicionarEndereco(Pessoa pessoa, Endereco endereco) {
        pessoa.addEndereco(endereco);
        pessoaRepository.salvar(pessoa);
    }

    // Método para remover um endereço
    public void removerEndereco(Pessoa pessoa, Endereco endereco) {
        pessoa.removeEndereco(endereco);
        pessoaRepository.salvar(pessoa);
    }

    public void salvarEndereco(Pessoa pessoa, Endereco endereco) {
        pessoa.getEndereco(endereco.getId());
        pessoaRepository.salvar(pessoa);
    }
}
