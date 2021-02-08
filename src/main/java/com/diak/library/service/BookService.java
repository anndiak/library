package com.diak.library.service;

import com.diak.library.entity.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> getAllBooks();
    Book findBookById(Long id);
    Book updateBook(Book book);
    void deleteBook(Long id);
    Book takeBook(Book book, Long user_id);
    Book giveBook(Book book, Long user_id);

}
