//package ru.otus.hw.repositories;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import ru.otus.hw.models.Author;
//import ru.otus.hw.models.Book;
//import ru.otus.hw.models.Comment;
//import ru.otus.hw.models.Genre;
//
//import java.util.List;
//import java.util.stream.IntStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
//@DataJpaTest
//@Import({JpaBookRepository.class, JpaGenreRepository.class, JpaAuthorRepository.class})
//class JpaBookRepositoryTest {
//
//    @Autowired
//    private JpaBookRepository repositoryJpa;
//
//    private List<Author> dbAuthors;
//
//    private List<Genre> dbGenres;
//
//    private List<Book> dbBooks;
//
//    private List<Comment> dbComments;
//
//    @BeforeEach
//    void setUp() {
//        dbAuthors = getDbAuthors();
//        dbGenres = getDbGenres();
//        dbComments = getDbComments();
//        dbBooks = getDbBooks(dbAuthors, dbGenres, dbComments);
//    }
//
//    @DisplayName("должен загружать книгу по id")
//    @ParameterizedTest
//    @MethodSource("getDbBooks")
//    void shouldReturnCorrectBookById(Book expectedBook) {
//        var actualBook = repositoryJpa.findById(expectedBook.getId());
//        assertThat(actualBook).isPresent()
//                .get()
//                .isEqualTo(expectedBook);
//    }
//
//    @DisplayName("должен загружать список всех книг")
//    @Test
//    void shouldReturnCorrectBooksList() {
//        var actualBooks = repositoryJpa.findAll();
//        var expectedBooks = dbBooks;
//
//        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
//        actualBooks.forEach(System.out::println);
//    }
//
//    @DisplayName("должен сохранять новую книгу")
//    @Test
//    void shouldSaveNewBook() {
//        var expectedBook = new Book(0, "BookTitle_10500", dbAuthors.get(0),
//                List.of(dbGenres.get(0), dbGenres.get(2)),
//                List.of(dbComments.get(0), dbComments.get(2)));
//        var returnedBook = repositoryJpa.save(expectedBook);
//        assertThat(returnedBook).isNotNull()
//                .matches(book -> book.getId() > 0)
//                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);
//
//        assertThat(repositoryJpa.findById(returnedBook.getId()))
//                .isPresent()
//                .get()
//                .isEqualTo(returnedBook);
//    }
//
//    @DisplayName("должен сохранять измененную книгу")
//    @Test
//    void shouldSaveUpdatedBook() {
//        var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2),
//                List.of(dbGenres.get(4), dbGenres.get(5)),
//                List.of(dbComments.get(4), dbComments.get(5)));
//
//        assertThat(repositoryJpa.findById(expectedBook.getId()))
//                .isPresent()
//                .get()
//                .isNotEqualTo(expectedBook);
//
//        var returnedBook = repositoryJpa.save(expectedBook);
//        assertThat(returnedBook).isNotNull()
//                .matches(book -> book.getId() > 0)
//                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);
//
//        assertThat(repositoryJpa.findById(returnedBook.getId()))
//                .isPresent()
//                .get()
//                .isEqualTo(returnedBook);
//    }
//
//    @DisplayName("должен удалять книгу по id ")
//    @Test
//    void shouldDeleteBook() {
//        assertThat(repositoryJpa.findById(1L)).isPresent();
//        repositoryJpa.deleteById(1L);
//        assertThat(repositoryJpa.findById(1L)).isEmpty();
//    }
//
//    private static List<Author> getDbAuthors() {
//        return IntStream.range(1, 4).boxed()
//                .map(id -> new Author(id, "Author_" + id))
//                .toList();
//    }
//
//    private static List<Genre> getDbGenres() {
//        return IntStream.range(1, 7).boxed()
//                .map(id -> new Genre(id, "Genre_" + id))
//                .toList();
//    }
//
//    private static List<Comment> getDbComments() {
//        return IntStream.range(1, 7).boxed()
//                .map(id -> new Comment(id, "Comment_" + id))
//                .toList();
//    }
//
//    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres, List<Comment> dbComment) {
//        return IntStream.range(1, 4).boxed()
//                .map(id -> new Book(id,
//                        "BookTitle_" + id,
//                        dbAuthors.get(id - 1),
//                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2),
//                        dbComment.subList((id - 1) * 2, (id - 1) * 2 + 2)
//                ))
//                .toList();
//    }
//
//    private static List<Book> getDbBooks() {
//        var dbAuthors = getDbAuthors();
//        var dbGenres = getDbGenres();
//        var dbComments = getDbComments();
//        return getDbBooks(dbAuthors, dbGenres, dbComments);
//    }
//}