package com.example.service.endereco;

import com.example.adapter.endereco.EnderecoCepAdapter;
import com.example.manager.ViaCepManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnderecoService {

    @Inject
    private ViaCepManager viaCepManager;

    public EnderecoCepAdapter consultarCep(String cep) {
        return viaCepManager.buscarDados(cep);
    }
}
