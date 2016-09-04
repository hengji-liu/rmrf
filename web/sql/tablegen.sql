DROP TABLE IF EXISTS `user`;
create table  `user`
(
  `id` int(11) not null,
  `username`        VARCHAR(30) comment 'username',
  `ps`              VARCHAR(30) comment 'password',
  `identity`        int(15) not null,
  `firstname`       VARCHAR(30),
  `lastname`        VARCHAR(30),
  `email`           VARCHAR(30),
  `birthday`        DATETIME,
  `address`         VARCHAR(30),
  `creditcard`      VARCHAR(30),
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Example insert*/
insert into `user` values ('1','li','12345','1','li','quan','liquan1992@outlook.com','1992-07-02',NULL,NULL);
