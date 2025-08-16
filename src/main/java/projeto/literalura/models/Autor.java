package projeto.literalura.models;


import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nomeAutor;
    private Integer anoDeNascimento;
    private Integer anoFalecimento;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Livros> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DadosAutor dadosAutor) {
        this.nomeAutor = dadosAutor.nomeAutor();
        this.anoDeNascimento = dadosAutor.anoDeNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    public List<Livros> getLivros() {
        return livros;
    }

    public void setLivros(List<Livros> livros) {
        this.livros = livros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    @Override
    public String toString() {
        List<String> titulos = livros.stream()
                .map(Livros::getTitulo)
                .toList();

        return "\n_________________________" +
                "\nNome do Autor: " + nomeAutor +
                "\nAno de Nascimento: " + anoDeNascimento +
                "\nAno do Falecimento: " + anoFalecimento +
                "\nLivros: " + titulos;

    }
}


