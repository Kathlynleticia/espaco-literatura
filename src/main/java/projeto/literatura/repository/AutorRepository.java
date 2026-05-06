package projeto.literatura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projeto.literatura.models.Autor;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT DISTINCT a FROM Livros l JOIN l.autores a JOIN FETCH a.livros")
    List<Autor> buscarTodosAutoresComLivros();

    @Query("select a from Autor a WHERE a.anoDeNascimento <= :ano and (a.anoFalecimento is null or a.anoFalecimento > :ano)")
    List<Autor> buscarAutoresVivosPorAno(int ano);
}
