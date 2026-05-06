package projeto.literatura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.literatura.models.Livros;

import java.util.List;

public interface LivrosRepository extends JpaRepository<Livros, Long> {

    List<Livros> findAllByOrderByTitulo();

    List<Livros> findByIdioma(String idioma);
}
