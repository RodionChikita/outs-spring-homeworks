package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@SpringBootTest
public class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider fileNameProvider;
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void setUp() {
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
    }

    @Test
    public void testFindAllThrowsException() {
        String testFileName = "non-existent-file.csv";
        when(fileNameProvider.getTestFileName()).thenReturn(testFileName);
        assertThrows(QuestionReadException.class, () -> csvQuestionDao.findAll());
    }
    @Test
    public void testFindAll() {
        String testFileName = "questions.csv";
        when(fileNameProvider.getTestFileName()).thenReturn(testFileName);
        assertDoesNotThrow(() -> csvQuestionDao.findAll());
    }
}