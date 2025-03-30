package com.example;

import com.example.adapter.endereco.EnderecoCepAdapter;
import com.example.manager.ViaCepManager;

public class Main {

    public static void main(String[] args) {
        ViaCepManager manager = new ViaCepManager();

        EnderecoCepAdapter adapter = manager.buscarDados("89221600");
        System.out.println(adapter);
    }
}
