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
  type_   INT(15) NOT NULL DEFAULT '1', /*1: normal user, 2 admin, 4 banned*/
  firstname  VARCHAR(30),
  lastname   VARCHAR(30),
  email      VARCHAR(30) NOT NULL ,
  birthday   DATE,
  address    VARCHAR(30),
  creditcard VARCHAR(30),
  img BLOB,
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
  authors VARCHAR(30),
  editors VARCHAR(30),
  title VARCHAR(30) NOT NULL ,
  year DATE,
  address VARCHAR(30),
  publisher VARCHAR(30),
  isbn VARCHAR(30),
  tag VARCHAR(30),/* tag for related item search*/
  paused BOOL NOT NULL ,/* 0 is false 1 is true*/
  img LONGBLOB,
  price INT(5),
  visited INT(5) /*if a book is visited by user, the count should be increased*/

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
  time_added DATETIME NOT NULL,
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
  VALUE ('hengji','Hengji','Liu','1234','liuhengji@outlook.com','1','Aydney','1992-1-1');

INSERT INTO `user` (username,firstname,lastname,ps,email,type_,address,birthday)
  VALUE ('liquan','Liquan','Luo','1234','luoliquan@outlook.com','1','Sydney','1992-1-2');

INSERT INTO `user` (username,firstname,lastname,ps,email,type_,address,birthday)
  VALUE ('ming','MingXuan','Hu','1234','luoliquan@outlook.com','1','Sydney','1992-1-2');

INSERT INTO `user` (username,firstname,lastname,ps,email,type_,address,birthday)
  VALUE ('linus','Li','Quan','1234','liquan1992@outlook.com','1','Sydney','1992-1-2');

INSERT INTO `book` (seller, book_type, title, price,paused) VALUE ('hengji','book','9321 full mark','100','0');
INSERT INTO `book` (seller, book_type, title, price,paused) VALUE ('hengji','book','9321 full mark II','110','0');
INSERT INTO `book` (seller, book_type, title, price,paused) VALUE ('hengji','book','9321 full mark III','120','0');
INSERT INTO `book` (seller, book_type, title, price,paused) VALUE ('hengji','book','js all go','100','0');

INSERT INTO `transaction` (seller, buyer, book_id, time) VALUE ('hengji','liquan','1','2016-9-10');

INSERT INTO `log_cart` (username, book_id, time_added, time_removed)
VALUE ('liquan','3','2016-9-9 23:25','2016-9-10 20:25');

INSERT INTO `log_cart` (username, book_id, time_added, time_removed)
  VALUE ('liquan','4','2016-9-9 23:25','2016-9-10 20:25');

INSERT into `cart` (username, book_id, time_addded)
VALUE ('liquan','1','2016-9-11 10:00');

INSERT into `cart` (username, book_id, time_addded)
  VALUE ('liquan','2','2016-9-11 11:00');

insert into `user_login` (username, time, granted) VALUE ('liquan','2016-9-10 23:25','1');
insert into `user_login` (username, time, granted) VALUE ('liquan','2016-9-11 23:25','1');
insert into `user_login` (username, time, granted) VALUE ('liquan','2016-9-10 20:25','0');

