### Basic Git Skill - 2
A full table for format:https://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History#pretty_format

#### Working with Remotes
If you've cloned your repository, you should at least see "origin". The default name git gives to the server you cloned from.

```
$git remote //see remote servers you have configured
$git remote -v //show url and shortname
```
(1) Adding and downloead Remote Repositories
```
$git remote add lt&shortname&gt <url>
$git remote add wl https://github.com/ge90toga/wl.git
$git fetch wl //promote username and password 
//fetch everything from ge90toga/wl
```
There similar things "download" data

fetch: pulls down all data that remote project that you don't have yet.
you should have references to all braches from that remote which can merge in or inspect at any time. (***Note:*** fetch only download to local repository, not working ***directory*** i.e you have to mannual merge it.)

clone: if you clone instead of fetch, it automatically adds remote repository with name origin. 

pull: track a remote branch. auto fetch and then merge branch into your current brach. 

(2) Upload:
```
//similar to downstream
$git remote add wl https://github.com/ge90toga/wl.git
$git push -u origin master
```

(3) Inspect remote
```
$git remote show [remote-name]
//shows the detail of push/pull and branching
```

(4)Removing and Renaming remotes

```
//renaming
$ git remote renmae [old-remotename] [new remote-name]
//removing 
$ git remote rm [server-name]
```

#### Undo things
(1) amend
replace the last commit with recommit
```
$ git commit -m 'initial commit'
$ git add forgotten_file
$ git commit --amend 
//recommit when you already commit, the latter replace the former.
```

(2) unstaging a file:

```
//wiered syntax but it works.
git reset HEAD <file_name>
```
(3) unmodified a modified file(revert back)

```
//usually use git -diff to compare before doing this
git checkout -- [filename] 
//(unmodified file from staging area to working directory)
git checkout HEAD [filenname]//recover from the last commit
git checkout HEAD~2 [filename]//recover from the 2 last comit 
```

#### Tagging
(1) Two types of tag: lightweight or annotated.
prefer annotated
```
//list tag
$ git tag
$ git tag -a -m "version info"
$ git show [tagname]
//we can tag commit 
$ git log --pretty=oneline
15027957951b64cf874c3557a0f3547bd83b3ff6 Merge branch 'experiment'
$ git tag -a v1.2 [hashcode]
```
(2) Sharing tages:
the git push command doesn’t transfer tags to remote servers. You will have to explicitly push tags to a shared server after you have created them.
```
$ git push [server] [tagname]
$ git push origin v1.0
$ git push origin --tags//push multiple tags at once
```
#### Alias
Create alias for easy operate.

```
git config [domain of effect] alias.[alianame] [operation]
git config --global alias.co checkout
//co will be equal to checkout

```
