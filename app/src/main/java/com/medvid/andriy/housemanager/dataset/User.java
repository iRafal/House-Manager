package com.medvid.andriy.housemanager.dataset;

/**
 * Created by Андрій on 5/22/2015.
 */
public class User {

    public static final String USER_DATA_JSON
            = "com.medvid.andriy.housemanager.dataset.UserJsonData";
    public static final String USER_ID_KEY
            = "com.medvid.andriy.housemanager.dataset.User.id";
    public static final String USER_NAME_KEY
            = "com.medvid.andriy.housemanager.dataset.User.name";
    public static final String USER_PASSWORD_KEY
            = "com.medvid.andriy.housemanager.dataset.User.password";
    public static final String IS_USER_ROOT_KEY
            = "com.medvid.andriy.housemanager.dataset.User.isRoot";

    private int id = 0;
    private String name = null;
    private String password = null;
    private boolean isUserRoot = false;

    public static User getRootUser(int id, String name,  String password)   {
        return new User(id, name, password, true);
    }

    public static User getSimpleUser(int id, String name,  String password)   {
        return new User(id, name, password, false);
    }

    public User(int id, String name,  String password, boolean isUserRoot )   {
        this.id = id;
        this.name = name;
        this.isUserRoot = isUserRoot;
        this.password = password;
    }

    public User()   {
        this(0, "", "", false);
    }


}
