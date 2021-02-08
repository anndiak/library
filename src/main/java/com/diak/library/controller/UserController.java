package com.diak.library.controller;

import com.diak.library.entity.Book;
import com.diak.library.entity.User;
import com.diak.library.service.BookService;
import com.diak.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public UserController(BookService bookService, UserService userService) {

        this.bookService = bookService;
        this.userService = userService;
    }

    @RequestMapping(path = "/books/{id}")
    @ResponseBody
    public ResponseEntity<List<Book>> getBooksById(@PathVariable("id") Long id){
        if(id == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        return new ResponseEntity<>(userService.getAllBooksByUser(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        if(userService.getUserById(id)== null){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        final User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User newuser){
        if(newuser == null){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        final User user = userService.addUser(newuser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User edit_user){
        final User user = userService.updateUser(edit_user, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        if(userService.getUserById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{user_id}/takes_book/{book_id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Book> takeBook(@PathVariable("user_id") Long user_Id, @PathVariable("book_id") Long book_id){
        Book book = bookService.findBookById(book_id);

        if(userService.getUserById(user_Id) == null ||  book== null || book.getId_user() != null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.takeBook(book,user_Id);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @RequestMapping(path = "/{user_id}/gives_book/{book_id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Book> giveBook(@PathVariable("user_id") Long user_Id, @PathVariable("book_id") Long book_id){
        Book book = bookService.findBookById(book_id);

        if(userService.getUserById(user_Id) == null ||  book == null || book.getId_user() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.giveBook(book,user_Id);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @RequestMapping(path = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<User,List<Book>>>getINFO(){
        Map<User,List<Book>> map = new HashMap<>();
        for(User user : userService.getUsers()){
            map.put(user,userService.getAllBooksByUser(user.getId()));
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }



}
