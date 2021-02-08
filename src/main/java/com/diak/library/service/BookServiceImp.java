package com.diak.library.service;

import com.diak.library.entity.Book;
import com.diak.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@Service("bookService")
public class BookServiceImp implements BookService{
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(book -> books.add(book));
        return books;
    }

    @Override
    @Transactional(readOnly=true)
    public Book findBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book takeBook(Book book, Long user_id) {
        book.setId_user(user_id);
        return bookRepository.save(book);
    }

    @Override
    public Book giveBook(Book book, Long user_id) {
        book.setId_user(null);
        return bookRepository.save(book);
    }

}
