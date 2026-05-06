package projeto.literatura.service;

public interface IconverteDados {

    <T> T pegarDados (String json, Class<T> classe);
}
