Ref: http://dev.mysql.com/doc/refman/5.7/en/
(1) Download mysql dmg(for mac) pkg from https://dev.mysql.com/downloads/mysql/
(2) Run mysql through system preference.
(3) set bashprofile

```
alias mysql=/usr/local/mysql/bin/mysql
alias mysqladmin=/usr/local/mysql/bin/mysqladmin
```
 
(3) login in to mysql:

```
mysql -u root -p
```

(4)

Rest password:

```
mysql> reset to 12345:
mysql> SET PASSWORD = PASSWORD('12345');
```

(5)
show all databases /create/switch to/drop database:
```
mysql> show DATABASES;
mysql> create DATABASE db_name;
mysql> use db_name;
mysql> drop DATABASE db_name;
```


