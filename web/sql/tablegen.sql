DROP TABLE IF EXISTS `transactions`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `log_cart`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `user_login`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
  username   VARCHAR(30),
  ps         VARCHAR(40) COMMENT 'password', /*use sha-1 encryption*/
  type_   INT(15) NOT NULL DEFAULT '1', /*1: normal user, 2 admin, 4 banned*/
  firstname  VARCHAR(30),
  lastname   VARCHAR(30),
  email      VARCHAR(30) NOT NULL ,
  birthday   DATE,
  address    VARCHAR(30),
  creditcard VARCHAR(30),
  PRIMARY KEY (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
ALTER TABLE user AUTO_INCREMENT = 100;

CREATE TABLE `user_login` /*for tracking user activity*/
(
  username   VARCHAR(30),
  FOREIGN KEY (username) REFERENCES user (username) ON DELETE CASCADE,
  time DATETIME NOT NULL,
  granted BOOL,
  ip VARCHAR(30),
  PRIMARY KEY (username,time)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE `book`
(
  book_id INT(15) PRIMARY KEY AUTO_INCREMENT,
  seller VARCHAR(30) COMMENT 'refer to user.usename',
  FOREIGN KEY (seller) REFERENCES user (username) ON DELETE CASCADE ,
  book_type  VARCHAR(30) ,/*Should be retricted to certain types such as article, inproceedings ...'*/
  authors VARCHAR(100),
  editors VARCHAR(100),
  title VARCHAR(100) NOT NULL ,
  year DATE,
  venue VARCHAR(30),
  publisher VARCHAR(30),
  isbn VARCHAR(30),
  tag VARCHAR(30),/* tag for related item search*/
  paused BOOL NOT NULL ,/* 0 is false 1 is true*/
  img LONGBLOB,
  price INT(5),
  visited INT(5) /*if a book is visited by user, the count should be increased*/

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE `transactions`
(
  seller  VARCHAR(30) COMMENT 'refer to user.usename',
  FOREIGN KEY (seller) REFERENCES user (username) ON DELETE CASCADE ,
  buyer VARCHAR(30) COMMENT 'refer to user.usename',
  FOREIGN KEY (buyer) REFERENCES user (username) ON DELETE CASCADE,
  book_id INT(15) REFERENCES book (book_id) ON DELETE RESTRICT,
  time DATETIME NOT NULL,
  PRIMARY KEY (seller,buyer,book_id)

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/*
stores alive cart item,
entry should be removed after purchase or remove operation,
if it is remove, it goes to log_cart
if it is purchase, just remove it and add into transaction of course.
*/
CREATE TABLE `cart`
(
  username VARCHAR(30) COMMENT 'refer to user.usename',
  FOREIGN KEY (username) REFERENCES user (username) ON DELETE CASCADE,
  book_id INT(15) REFERENCES book (book_id) ON DELETE RESTRICT,
  time_addded DATETIME NOT NULL,
  PRIMARY KEY (username,book_id)

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/*
stores items added to cart but was removed without purchasing, if later on user purchases this item,
it should also be removed.
*/
CREATE TABLE `log_cart`
(
  username VARCHAR(30) NOT NULL COMMENT 'refer to user.usename',
  FOREIGN KEY (username) REFERENCES user (username) ON DELETE CASCADE,
  book_id INT(15) NOT NULL REFERENCES book (book_id) ON DELETE RESTRICT,
  time_added DATETIME NOT NULL,
  time_removed DATETIME NOT NULL,
  PRIMARY KEY (username,book_id)

) ENGINE = InnoDB DEFAULT CHARSET = utf8;