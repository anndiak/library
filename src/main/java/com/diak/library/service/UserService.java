package com.diak.library.service;

import com.diak.library.entity.Book;
import com.diak.library.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getUsers();
    User getUserById(Long id);
    User updateUser(User user, Long id);
    void deleteUser(Long id);
    List<Book> getAllBooksByUser(Long id);

}
