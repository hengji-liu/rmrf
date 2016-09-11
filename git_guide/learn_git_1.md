### Basic Git Skill - 1

### git has three 3 main sections:

https://git-scm.com/book/en/v2/book/01-introduction/images/areas.png

[Working directory] <-> [staging area] <-> [Repository]

#### Git directory initialisation
- init git from existing direcory:
enter project directory type:

```shell
$ git init
```

- clone from github 

```shell
$ git clone https://github.com/xxx/xxx //or
$ git clone https://github.com/xxx/project_name my_new_projectname
```

#### Recording changes:

Some state: and how you it transision from another.
Untracked (add) 
Staged (commit) 
unmodified (edit) 
modified(add) staged

(1) Check status:

```
git status
git status -s //short version
```

(2) Add

Add file to staging area.

```
$ git add filename 
```
When you commit/staged and modify, you get "changes staged not commit..."
you need to restage it! by "git add"

(3) Ignoring files

create .gitignore


(4) Viewing staged and unstaged changes and delete staging file

```
$git diff 
$git diff filename
//check the difference between staged and modified files in working directory
//this one checks working directory vs staged

$ git diff --staged or --cached
//see what've staged that will go into next commit.
//this one check staged vs your last commit.
```
More about git diff
```
$ git diff filename //compares the last staged with your working direcotry
$ git diff HEAD filename //compares the last commited with your working directory
$ git diff HEAD~2 HEAD filename//compares the last commited with the 2nd last commit.
```
$ git rm --cached filename
```
*Note*: When you modify staged file, you could get both file in "changes to be comited and not staged". Simply because you have older version staged but you also modified it in your own directory. To see what you’ve changed but not yet staged, type "git diff"
or use ***git difftool***, with more graphical!

(5) Ignoring files
Use some rules https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository#Ignoring-Files

#### Commit
commit to repository (local)
```
git commit //will call editor
git commit -m "commit message"
git commit -v //put diff into message to see what you modified.
```

#### Deletion & Move
```
git rm xxx
git rm --staged filename

git mv file_from file_to
//rename or move
//git figures out rename implicitly.
```
Git figures out that it’s a rename implicitly, so it ***doesn’t matter*** if you rename a file that way or with the mv command. 

#### Viewing the Commit History
https://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History
```
git log // display all commits in reverse chronological order.
git log -p //show diffrenece introduced in each commit
git log -p -2//limits only 2 entries.
git log --stat
/* abbreviated stats for each commit, how many files were changed, and how many lines in those files were added and removed. It also puts a summary of the information at the end.*/

git log --pretty=oneline/short/full //show log in other format
git log --pretty=format:"%h - %an, %ar : %s" //format the output
git log --pretty=format:"%h %s" --graph//show commits in graph
```