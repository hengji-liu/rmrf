package daoIterface;

import bean.User;

import java.util.List;

/**
 * Created by Linus on 10/09/2016.
 */
public interface UserDao {
    /**
     * find All user currently using the system (Admin not included)
     * @return
     */
    public List<User> getAllUser();
    /**
     * search a certain user by name(Including username, firstname, lastname)
     * @param keyWord
     * @return
     */
    public List<User> searchUserByName(String keyWord);

    /**
     * Get a user based on username
     * @param userName
     * @return User object or null if userName does not match any user;
     */
    public User getUserByUserName(String userName);
}
