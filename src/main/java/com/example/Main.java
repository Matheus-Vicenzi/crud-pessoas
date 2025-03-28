package com.example;

import com.example.adapter.CepAdapter;
import com.example.manager.ViaCepManager;

public class Main {

    public static void main(String[] args) {
        ViaCepManager manager = new ViaCepManager();

        CepAdapter adapter = manager.buscarDados("89221600");
        System.out.println(adapter);
    }
}
