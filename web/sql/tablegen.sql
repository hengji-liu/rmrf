DROP TABLE IF EXISTS `transaction`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `log_cart`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
  id         INT(15) PRIMARY KEY AUTO_INCREMENT,
  username   VARCHAR(30) UNIQUE COMMENT 'username',
  ps         VARCHAR(128) COMMENT 'password',
  identity   INT(15) NOT NULL DEFAULT '1',
  firstname  VARCHAR(30),
  lastname   VARCHAR(30),
  email      VARCHAR(30) NOT NULL ,
  birthday   DATETIME,
  address    VARCHAR(30),
  creditcard VARCHAR(30),
  img BLOB
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

ALTER TABLE user AUTO_INCREMENT = 100;


CREATE TABLE `book`
(
  book_id INT(15) PRIMARY KEY AUTO_INCREMENT,
  seller  INT(15) COMMENT 'refer to user.usename',
  FOREIGN KEY (seller) REFERENCES user (id) ON DELETE CASCADE ,
  type  VARCHAR(30) ,/*Should be retricted to certain types such as article, inproceedings ...'*/
  authors VARCHAR(30),
  editors VARCHAR(30),
  title VARCHAR(30) NOT NULL ,
  year VARCHAR(30),
  address VARCHAR(30),
  publisher VARCHAR(30),
  isbn VARCHAR(30),
  tag VARCHAR(30),/* tag for related item search*/
  paused BOOL NOT NULL ,/* 0 is false 1 is true*/
  img LONGBLOB,
  price INT(5)

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE `transaction`
(
  transaction_id INT(15) PRIMARY KEY AUTO_INCREMENT,
  seller INT(15) COMMENT 'refer to user.usename',
  FOREIGN KEY (seller) REFERENCES user (id) ON DELETE CASCADE ,
  buyer INT(15) COMMENT 'refer to user.usename',
  FOREIGN KEY (buyer) REFERENCES user (id) ON DELETE CASCADE,
  book_id INT(15) REFERENCES book (book_id) ON DELETE RESTRICT,
  time DATETIME NOT NULL

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/*
stores alive cart item,
entry should be removed after purchase or remove operation,
if it is remove, it goes to log_cart
if it is purchase, just remove it and add into transaction of course.
*/
CREATE TABLE `cart`
(
  cart_id INT(15) PRIMARY KEY AUTO_INCREMENT,
  user_id INT(15) COMMENT 'refer to user.usename',
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
  book_id INT(15) REFERENCES book (book_id) ON DELETE RESTRICT,
  time_addded DATETIME NOT NULL

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/*
stores items added to cart but was removed without purchasing, if later on user purchases this item,
it should also be removed.
*/
CREATE TABLE `log_cart`
(
  cart_id INT(15) PRIMARY KEY AUTO_INCREMENT,
  user_id INT(15) COMMENT 'refer to user.usename',
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
  book_id INT(15) REFERENCES book (book_id) ON DELETE RESTRICT,
  time_addded DATETIME NOT NULL,
  time_removed DATETIME NOT NULL

) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/*Example insert
INSERT INTO `user` values (NULL,'li','12345','1','li','quan','liquan1992@outlook.com','1992-07-02',NULL,NULL,NULL);
INSERT INTO `user` (username,email) VALUE ('linus','12345@gmail.com');
INSERT INTO `book` (seller,title,paused,price) VALUES('100','the jsp','0','122');
INSERT INTO `book` (seller,title,paused,price) VALUES('100','the jsp 2','0','10');
INSERT INTO `transaction`(seller,buyer,book_id,time) VALUE ('100','101','1','2016-9-9 21:46:00');
*/