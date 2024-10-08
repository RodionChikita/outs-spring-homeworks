import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.StudentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {
    private LocalizedIOService localizedIOService;

    private StudentService studentService;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final Student TEST_STUDENT = new Student(FIRST_NAME, LAST_NAME);

    @BeforeEach
    public void setUp() {
        localizedIOService = mock();
        studentService = new StudentServiceImpl(localizedIOService);
    }

    @Test
    public void testDetermineCurrentStudent() {
        when(localizedIOService.readStringWithPromptLocalized("StudentService.input.first.name")).thenReturn(FIRST_NAME);
        when(localizedIOService.readStringWithPromptLocalized("StudentService.input.last.name")).thenReturn(LAST_NAME);
        Student result = studentService.determineCurrentStudent();
        assertEquals(TEST_STUDENT, result);
    }
}
