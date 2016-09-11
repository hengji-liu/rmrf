### Basic Git Skill - 3

#### Branching

(1) Create a new branch:
How does Git know what branch you’re currently on? 
It keeps a special pointer called ***HEAD***.

```
$ git branch [new branchname]
$ git log --oneline --decorate //check HEAD is pointing which branch
$ git checkout [branch] //chang HEAD to a branch
$ git log --oneline --decorate --graph --all //very useful!!!
```

(2) An example of braching merge and confilct resolve

Suppose the following sencario:

```
$ git branch friday
//do something... e.g. add this in DNode.java
$ git checkout master
//back to no this

//we find main not working
// from master, we create :
$ git brach hot fix
$ git checkout hotfix
//fix hot fix bug and commit, assume we touched something
//also being modifeid on friday
//back to master
$ git checkout master

$ git merge friday
//conflict

$ git status //tells more info about conflicts "both modified"

// we can see conflicts in the file marked
$ git mergetool//we can use this tool to merge! or just modify the file

```


#### Branch control

```
$ git branch //see all braches, * means current
$ git branch -v // see last commit of each branch
$ git branch --merged // it is ok to delete branches without *
$ git branch --no-merged // see contains work not merged

```
