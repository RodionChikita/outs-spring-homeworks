package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.*;
import ru.otus.hw.service.LocalizedIOServiceImpl;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestService.class)
public class TestServiceImplTest {

    @Mock
    private LocalizedIOServiceImpl ioService;

    @Mock
    private QuestionDao questionDao;

    private TestServiceImpl testService;
    private static final Answer TEST_ANSWER_1 = new Answer("Science doesn't know this yet", true);
    private static final Answer TEST_ANSWER_2 = new Answer("Certainly. The red UFO is from Mars. And green is from Venus", true);
    private static final Answer TEST_ANSWER_3 = new Answer("Absolutely not", true);
    private static final Question TEST_QUESTION = new Question("Is there life on Mars?", List.of(TEST_ANSWER_1, TEST_ANSWER_2, TEST_ANSWER_3));
    private static final List<Question> TEST_QUESTIONS = new ArrayList<>(List.of(TEST_QUESTION));
    private static final Student STUDENT = new Student("John", "Doe");

    @BeforeEach
    public void setUp() {
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    public void testExecuteTestFor() {
        when(questionDao.findAll()).thenReturn(TEST_QUESTIONS);
        when(ioService.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1);

        TestResult testResult = testService.executeTestFor(STUDENT);

        assertEquals(1, testResult.getAnsweredQuestions().size());
        assertEquals(1, testResult.getRightAnswersCount());

        verify(ioService, times(1)).printLine("");
        verify(ioService, times(1)).printFormattedLineLocalized("TestService.answer.the.questions");
        verify(ioService, times(1)).printFormattedLine("1. %s", "Is there life on Mars?");
        verify(ioService, times(1)).printFormattedLine("  1) %s", "Science doesn't know this yet");
        verify(ioService, times(1)).printFormattedLine("  2) %s", "Certainly. The red UFO is from Mars. And green is from Venus");
        verify(ioService, times(1)).printFormattedLine("  3) %s", "Absolutely not");
    }
}