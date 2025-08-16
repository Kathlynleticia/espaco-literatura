package projeto.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(@JsonAlias("title")String titulo,
                          @JsonAlias("languages")List<String> idioma,
                          @JsonAlias("authors")List<DadosAutor> autores,
                          @JsonAlias("download_count") Integer download) {
}
