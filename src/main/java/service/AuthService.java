package service;

public interface AuthService {

    boolean registerUser(String login, String password, String name); // class model of user??? todo

    boolean loginUser(String login, String password);
}
