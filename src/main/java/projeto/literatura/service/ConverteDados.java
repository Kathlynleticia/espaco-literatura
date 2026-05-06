package projeto.literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ConverteDados implements IconverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T pegarDados(String json, Class<T> classe) {
        if (json == null || json.trim().isEmpty()) {
            throw new RuntimeException("❗ O JSON retornado está vazio. Verifique a URL ou a resposta da API.");
        }

        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("❌ Erro ao converter JSON: " + e.getMessage(), e);
        }
    }
}
