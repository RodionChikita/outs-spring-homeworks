package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent(value = "Testing system")
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    @ShellMethod(value = "Run test", key = {"r", "run"})
    @Override
    public void run() {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
