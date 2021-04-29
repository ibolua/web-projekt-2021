package de.hsrm.mi.web.projekt.login;

public class LoggedInUsername {

    private String userName;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void add(String username2) {
        setUserName(username2);
    }


}
