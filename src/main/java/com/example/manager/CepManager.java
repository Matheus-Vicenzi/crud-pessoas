package com.example.manager;

import com.example.adapter.endereco.EnderecoCepAdapter;

public interface CepManager {
    public EnderecoCepAdapter buscarDados(String cep);
}
