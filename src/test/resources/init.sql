CREATE TABLE `users` (
                         `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                         `user_name` varchar(50) not null ,
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `books` (
                         `book_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                         `book_name` varchar(50) NOT NULL,
                         `user_id` int(11),
                         PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users(user_id, user_name)
VALUES (1,'Tom')
     ,(2,'Ron')
     ,(3,'Richard')
     ,(4,'David')
     ,(5,'Emma');

INSERT INTO books(book_id, book_name, user_id)
    VALUE (1,'Harry Potter',1)
    ,(2,'New Moon',1)
    ,(3,'One Day',2)
    ,(4,'Atonement',2)
    ,(5,'Life of Pi',3)
    ,(6,'Birdsong',4);
