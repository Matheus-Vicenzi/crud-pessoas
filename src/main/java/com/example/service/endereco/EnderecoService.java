package com.example.service.endereco;

import com.example.adapter.CepAdapter;
import com.example.manager.ViaCepManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnderecoService {

    @Inject
    private ViaCepManager viaCepManager;

    public CepAdapter consultarCep(String cep) {
        return viaCepManager.buscarDados(cep);
    }
}
