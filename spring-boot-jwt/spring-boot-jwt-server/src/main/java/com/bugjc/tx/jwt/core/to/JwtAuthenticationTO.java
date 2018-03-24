package com.bugjc.tx.jwt.core.to;

/**
 * jwt数据传输对象
 */
public class JwtAuthenticationTO {
    private String username;
    private String password;

    public JwtAuthenticationTO() {
        super();
    }

    public JwtAuthenticationTO(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
