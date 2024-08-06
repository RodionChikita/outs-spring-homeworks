package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor
@Setter
@Component
public class CsvQuestionDao implements QuestionDao {
    private static final Function<QuestionDto, Question> MAP_FROM_DTO_FUNCTION =
            q -> new Question(q.getText(), q.getAnswers());

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(fileNameProvider.getTestFileName()));
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader);
        ) {
            var csvReader = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            return csvReader.stream().toList().stream().map(MAP_FROM_DTO_FUNCTION).toList();
        } catch (Exception ex) {
            throw new QuestionReadException(ex.getMessage(), ex);
        }
    }
}

