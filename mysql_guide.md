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
reset to 12345:
SET PASSWORD = PASSWORD('12345');
```

