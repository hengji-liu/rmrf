package daoIterface;

import bean.LoginLog;
import bean.Pager;
import bean.User;

import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public interface UserDao {


    Pager<User> getUserPage(int page_num);
    Pager<User> searchUser(String username,int page_num);
    /**
     * Get a user based on username
     * @param userName
     * @return User object or null if userName does not match any user;
     */
    public User getUserByUserName(String userName);

    /**
     * Get users login history
     * @param userName
     * @return
     */
    public List<LoginLog> getLoginLog(String userName);

    User getUserWhenLogin(String username, String ps);
    void confirm(String username);
    int save(User u);
}
