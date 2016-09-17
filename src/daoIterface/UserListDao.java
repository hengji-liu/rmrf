package daoIterface;

import bean.Pager;
import bean.User;
/**
 * Created by Linus on 16/09/2016.
 */
public interface UserListDao {
    Pager<User> getUserPage(int page_num);
}
