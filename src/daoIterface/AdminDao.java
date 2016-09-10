package daoIterface;

import util.EncryptionUtil;

/**
 * Created by Linus on 10/09/2016.
 */
public interface AdminDao {
    /**
     *
     * @param userName
     * @param password
     * @return true if login success
     */
    public boolean adminLogin(String userName,String password);


}
