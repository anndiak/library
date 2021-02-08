package com.diak.library.controller;

import com.diak.library.entity.Book;
import com.diak.library.entity.User;
import com.diak.library.service.BookService;
import com.diak.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {

        this.bookService = bookService;
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{id}")
    @ResponseBody
    public ResponseEntity<User>getUserByBook(@PathVariable("id") Long id)
    {
        if(bookService.findBookById(id) == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(userService.getUserById(book.getId_user()),HttpStatus.OK);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Book>> getAllBooks(){
        final List<Book> bookList = bookService.getAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id){
        if(bookService.findBookById(id) == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        final Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Book> addBook(@RequestBody Book newbook){
        Long user_id = newbook.getId_user();
        if(user_id != null && userService.getUserById(user_id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Book book = bookService.addBook(newbook);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book edit_book){
        Long user_id = edit_book.getId_user();
        if(user_id != null && userService.getUserById(user_id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Book book = bookService.updateBook(edit_book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id) {
        if(bookService.findBookById(id) == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
