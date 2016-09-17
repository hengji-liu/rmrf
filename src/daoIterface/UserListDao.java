package daoIterface;

import bean.Pager;
import bean.User;
/**
 * Created by Linus on 16/09/2016.
 */
public interface UserListDao {
    public int getTotalUserCount();
    Pager<User> getUserPage(int page_num);
    public int getTotalUserSearchCount(String keyWord);
    Pager<User> searchUser(String username,int page_num);


}
