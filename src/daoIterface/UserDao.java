package daoIterface;

import bean.LoginLog;
import bean.User;

import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public interface UserDao {


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

    public User getUserWhenLogin(String username, String ps);
    public int save(User u);
}
