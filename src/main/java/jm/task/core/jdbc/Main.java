package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        UserService userService = new UserServiceImpl(userDaoHibernate);
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
