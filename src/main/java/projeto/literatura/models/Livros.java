package projeto.literatura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
public class Livros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "titulos")
    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idioma;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores = new ArrayList<>();
    private Integer download;


    public Livros(ResultadosApi dados) {
    }

    public Livros() {
    }

    public Livros(DadosLivros dadosLivros) {
        this.titulo = dadosLivros.titulo();
        this.idioma = dadosLivros.idioma();
        this.autores = dadosLivros.autores().stream()
                .map(Autor::new) // usa o construtor Autores(DadosAutor dados)
                .collect(Collectors.toList());
        this.download = dadosLivros.download();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    @Override
    public String toString() {
        String autoresFormatados = autores.stream()
                .map(Autor::getNomeAutor)
                .collect(Collectors.joining(","));

        return "\nLivro: " + titulo +
                "\nIdioma(s): " + idioma +
                "\nQuantidade de Downloads: " + download +
                "\nAutores: " + autoresFormatados;
    }
}
