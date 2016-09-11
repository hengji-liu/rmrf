### Basic Git Skill - 4

#### Branching Workfolows
<a href = "https://git-scm.com/book/en/v2/Git-Branching-Branching-Workflows">Just an simple example</a>

#### <a href="https://git-scm.com/book/en/v2/Git-Branching-Remote-Branches">Working with Remote branch</a>

#### Remote branches

```
$ git ls-remote [remote]
//you(li) create master branch on remote 
//linus modified and commit master on remote
// to synchronise work, you use 
$ git fetch origin
// but you still need to merge your master! with remote: origin/master, you can use
$ git pull //or merge with your current by: 
$ git merge origin/serverfix //if you want to merge

// you do somework on a certain branch and decided to share: 
$ git push <remote> <branch>
```

#### Tracking Branches

Checking out a local branch from a remote-tracking branch automatically creates what is called a “tracking branch” (and the branch it tracks is called an “upstream branch”). If you’re on a tracking branch and type git pull, Git automatically knows which server to fetch from and branch to merge into.

```
$ git checkout -b [branch] [remotename]/[branch] //common way to create tracking branch.
$ git checkout --track origin/serverfix //same just shorter version
$ $ git checkout serverfix //even shorter !!! Git will create a tracking branches for you
```
If you already have a local branch and want to set it to a remote branch you just pulled down, or want to change the upstream branch you’re tracking, you can use the -u or --set-upstream-to option to git branch to explicitly set it at any time.
```
$ $ git branch -u origin/serverfix
```

#### Pulling
While the git fetch command will fetch down all the changes on the server that you don’t have yet, it will not modify your working directory at all. It will simply get the data for you and let you merge it yourself. However, there is a command called ***git pull*** which is essentially a git fetch immediately followed by a *git merge* in most cases. Generally it’s better to simply use the fetch and merge.

#### Deleting
```
git push origin --delete serverfix
```
