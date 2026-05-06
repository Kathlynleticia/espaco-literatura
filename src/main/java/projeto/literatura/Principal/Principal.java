package projeto.literalura.Principal;

import projeto.literalura.models.*;
import projeto.literalura.repository.AutorRepository;
import projeto.literalura.repository.LivrosRepository;
import projeto.literalura.service.ConsumoApi;
import projeto.literalura.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final Scanner leitura = new Scanner(System.in);
    private static final ConsumoApi consumo = new ConsumoApi();
    private static final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private LivrosRepository repositorio;
    private  AutorRepository autorRepository;
    private String tituloLivro;

    public Principal(LivrosRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
    }
    public void exibeMenu() {

        var opcao = -1;
        do {
            var menu = """
                    \n1 - Buscar Livro pelo título
                    2 - listar livros registrados
                    3 - Listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determiado idioma
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (opcao != 0);
    }

    private void buscarLivroWeb() {
        ResultadosApi dados = getResultadoApi();

        Optional<DadosLivros> livrosFiltrados = dados.resultados().stream()
                .filter(o -> o.titulo().equalsIgnoreCase(tituloLivro))
                .max(Comparator.comparingInt(DadosLivros::download));

        if (livrosFiltrados.isPresent()) {
            DadosLivros livro = livrosFiltrados.get();

            Livros livros = new Livros(dados);

            livros.setTitulo(livro.titulo());
            livros.setIdioma(livro.idioma());
            livros.setDownload(livro.download());

            List<Autor> autores = Optional.ofNullable(livro.autores())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(dadosAutor -> {
                        Autor autor = new Autor();
                        autor.setNomeAutor(dadosAutor.nomeAutor());
                        autor.setAnoDeNascimento(dadosAutor.anoDeNascimento());
                        autor.setAnoFalecimento(dadosAutor.anoFalecimento());
                        return autor;
                    })
                    .collect(Collectors.toList());

            livros.setAutores(autores);
            repositorio.save(livros);

            System.out.println("\n******* LIVRO SALVO ********" + livros +
                    "\n*********************");
        } else {
            System.out.println("Nenhum livro com o título exato \"" + tituloLivro + "\" foi encontrado.");
        }
    }

    private ResultadosApi getResultadoApi() {
        System.out.println("Digite o título do livro: ");
        this.tituloLivro = leitura.nextLine();

        var url = ENDERECO + tituloLivro.replace(" ", "+");
        var json = consumo.obterDados(url);
        return conversor.pegarDados(json, ResultadosApi.class);
    }

    private void listarLivrosRegistrados() {
        List<Livros> livros = repositorio.findAllByOrderByTitulo();
        livros.forEach(System.out::println);
    }


    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.buscarTodosAutoresComLivros();
        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano para ver os autores vivos: ");
        var ano = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autoresVivos = autorRepository.buscarAutoresVivosPorAno(ano);
        autoresVivos.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para realizar a busca: " +
                "\npt - Português" +
                "\nen - Inglês" +
                "\nfr - Francês" +
                "\nes - Espanhol");

        var idioma = leitura.nextLine().trim().toLowerCase();
        List<Livros> livrosIdioma = repositorio.findByIdioma(idioma);
        if (livrosIdioma.isEmpty()) {
            System.out.println("\n********** Ainda não temos livros nesse idioma! *****************");
        } else {
            livrosIdioma.forEach(System.out::println);
        }
    }

}




