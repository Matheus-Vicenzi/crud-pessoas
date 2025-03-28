package com.example.manager;

import com.example.adapter.CepAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@ApplicationScoped
public class ViaCepManager implements CepManager {
    private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";

    @Override
    public CepAdapter buscarDados(String cep) {
        String urlString = String.format(VIACEP_URL, cep);
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Erro ao consultar ViaCEP: " + conn.getResponseCode());
            }

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();

            if (jsonObject.has("erro")) {
                throw new RuntimeException("CEP inválido ou não encontrado");
            }

            return new CepAdapter(
                    jsonObject.get("cep").getAsString(),
                    jsonObject.get("logradouro").getAsString(),
                    jsonObject.get("localidade").getAsString(),
                    jsonObject.get("bairro").getAsString(),
                    jsonObject.get("uf").getAsString()
            );
        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar CEP: " + e.getMessage(), e);
        }
    }
}
