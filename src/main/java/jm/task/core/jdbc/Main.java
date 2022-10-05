package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Boris", "Godunov", (byte) 54);
        userService.saveUser("Aleksandr", "Pushkin", (byte) 37);
        userService.saveUser("Lev", "Tolstoy", (byte) 82);
        userService.saveUser("Nikolay", "Rurik", (byte) 17);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
