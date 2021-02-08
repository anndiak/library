package com.diak.library;

import com.diak.library.entity.Book;
import com.diak.library.entity.User;
import com.diak.library.repository.BookRepository;
import com.diak.library.repository.UserRepository;
import com.diak.library.service.BookService;
import com.diak.library.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServiceTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testUpdateBook_notfound(){
        doThrow(new EntityNotFoundException()).when(bookRepository).findById(1L);
        Book book = new Book(1L,"BOOK1",null);
        bookService.updateBook(book);
    }

    @Test
    public void getAllBooks(){
        when(bookRepository.findAll()).thenReturn(Stream
                .of(new Book(1L,"BOOK1",null),new Book(1L,"BOOK2",null)).collect(Collectors.toList()));
        assertEquals(2,bookService.getAllBooks().size());
    }

    @Test
    public void getBookById() {
        Book book = new Book(1L,"BOOK1",null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book result =  bookService.findBookById(1L);
        Long id = result.getId();
        assertEquals(Optional.of(1L),Optional.ofNullable(id));
        assertEquals("BOOK1", result.getName());
        assertEquals(null,result.getId_user());
    }

    @Test
    public void saveBookTest() {
        Book book = new Book(1L,"BOOK1",null);
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.addBook(book));
    }

    @Test
    public void testUpdateBook(){
        Book book1 = new Book(1L,"BOOK",null);
        Book book2 = new Book(1L,"BOOK_update",null);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        Mockito.when(bookRepository.save(book1)).thenReturn(book2);

        Book result =bookService.updateBook(book1);

        Assert.assertEquals("BOOK_update", result.getName());
    }

    @Test
    public void deleteBookTest() {
        Book book = new Book(1L,"BOOK1",null);
        bookService.deleteBook(book.getId());
        verify(bookRepository, times(1)).deleteById(book.getId());
    }




    @Test
    public void testUpdateUser_notfound(){
        doThrow(new EntityNotFoundException()).when(userRepository).findById(1L);
        User user = new User(1L,"User");
        userService.updateUser(user, 1L);
    }

    @Test
    public void getAllUsers(){
        when(userRepository.findAll()).thenReturn(Stream
                .of(new User(1L,"User"),new User(2L,"User")).collect(Collectors.toList()));
        assertEquals(2,userService.getUsers().size());
    }
    @Test
    public void UserById() {
        User user  = new User(1L,"User");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result =  userService.getUserById(1L);
        Long id = result.getId();
        assertEquals(Optional.of(1L),Optional.ofNullable(id));
        assertEquals("User", result.getName());
    }

    @Test
    public void saveUserTest() {
        User user  = new User(1L,"User");
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.addUser(user));
    }

    @Test
    public void testUpdateUser(){
        User user1 = new User(1L,"User");
        User user2 = new User(1L,"User_update");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.save(user1)).thenReturn(user2);

        User result =userService.updateUser(user1,1L);

        Assert.assertEquals("User_update", result.getName());
    }

    @Test
    public void deleteUserTest() {
        User user = new User(1L,"User");
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }


}
