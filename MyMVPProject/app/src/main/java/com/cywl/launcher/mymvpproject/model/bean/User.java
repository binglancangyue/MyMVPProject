package com.cywl.launcher.mymvpproject.model.bean;

public class User {
    private String login;
    private String id;
    private String node_id;

    public User(String login, String id, String node_id) {
        this.login = login;
        this.id = id;
        this.node_id = node_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id='" + id + '\'' +
                ", node_id='" + node_id + '\'' +
                '}';
    }
}
