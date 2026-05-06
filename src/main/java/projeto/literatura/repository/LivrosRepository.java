package projeto.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projeto.literalura.models.Livros;

import java.util.List;
import java.util.Optional;

public interface LivrosRepository extends JpaRepository<Livros, Long> {

    List<Livros> findAllByOrderByTitulo();

    List<Livros> findByIdioma(String idioma);
}
