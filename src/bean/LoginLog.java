package bean;

import java.io.Serializable;

/**
 * Created by Linus on 11/09/2016.
 */
public class LoginLog implements Serializable{
    private User user;
    private String time;
    private boolean granted;

    public User getUser() {
        return user;
    }

    public void setUserByName(String userName) {
        this.user = new User();
        this.user.setUsername(userName);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }
}
