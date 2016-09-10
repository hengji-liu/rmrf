DROP TABLE IF EXISTS `transaction`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `log_cart`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `user_login`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
  username   VARCHAR(30),
  ps         VARCHAR(40) COMMENT 'password', /*use sha-1 encryption*/
  type_   INT(15) NOT NULL DEFAULT '1', /*1: normal user, 2 admin*/
  firstname  VARCHAR(30),
  lastname   VARCHAR(30),
  email      VARCHAR(30) NOT NULL ,
  birthday   DATETIME,
  address    VARCHAR(30),
  creditcard VARCHAR(30),
  img BLOB,
  PRIMARY KEY (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
ALTER TABLE user AUTO_INCREMENT = 100;

CREATE TABLE `user_login` /*for tracking user activity*/
(
  user_   VARCHAR(30),
  FOREIGN KEY (user_) REFERENCES user (username) ON DELETE CASCADE,
  time DATE NOT NULL,
  PRIMARY KEY (user_,time)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE `book`
(
  book_id INT(15) PRIMARY KEY AUTO_INCREMENT,
  seller VARCHAR(30) COMMENT 'refer to user.usename',
  FOREIGN KEY (seller) REFERENCES user (username) ON DELETE CASCADE ,
  book_type  VARCHAR(30) ,/*Should be retricted to certain types such as article, inproceedings ...'*/
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
  time_addded DATETIME NOT NULL,
  time_removed DATETIME NOT NULL,
  PRIMARY KEY (username,book_id)

) ENGINE = InnoDB DEFAULT CHARSET = utf8;



/*
INSERT INTO `user` (username,ps,email,identity) VALUE ('liquan','123','liquan1992@outlook.com','2');
INSERT INTO `user` (username,ps,email,identity) VALUE ('luoliquan','456','louliquan@email.com','1');
*/
/*
INSERT INTO `book` (seller,title,paused,price) VALUES('100','the jsp','0','122');
INSERT INTO `book` (seller,title,paused,price) VALUES('100','the jsp 2','0','10');
INSERT INTO `transaction`(seller,buyer,book_id,time) VALUE ('100','101','1','2016-9-9 21:46:00');
*/


INSERT INTO `user` (username,ps,email,type_) VALUE ('li','456','liquan1992@email.com','2');

INSERT INTO `user` (username,firstname,lastname,ps,email,type_,address,birthday)
  VALUE ('hengji','hengji','liu','1234','liuhengji@outlook.com','1','sydney','1992-1-1');

INSERT INTO `user` (username,firstname,lastname,ps,email,type_,address,birthday)
  VALUE ('liquan','liquan','luo','2345','luoliquan@outlook.com','1','sydney','1992-1-2');

INSERT INTO `book` (seller, book_type, title, price,paused) VALUE ('hengji','book','9321 full mark','100','0');
INSERT INTO `book` (seller, book_type, title, price,paused) VALUE ('hengji','book','9321 full mark II','110','0');
INSERT INTO `book` (seller, book_type, title, price,paused) VALUE ('hengji','book','9321 full mark III','120','0');

INSERT INTO `transaction` (seller, buyer, book_id, time) VALUE ('hengji','liquan','1','2016-9-10');

