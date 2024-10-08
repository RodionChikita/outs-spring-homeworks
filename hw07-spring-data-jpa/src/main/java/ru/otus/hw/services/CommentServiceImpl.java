package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Transactional
    @Override
    public Comment insert(String text, Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        var comment = new Comment(0L, text, book);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment update(Long id, String text) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    private Comment save(Long id, String text, Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        var comment = new Comment(id, text, book);
        return commentRepository.save(comment);
    }
}
