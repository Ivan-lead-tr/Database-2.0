package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Ivan", "Ivanov", (byte) 30);
        us.saveUser("Petr", "Petrov", (byte) 40);
        us.saveUser("Oleg", "Bykov", (byte) 25);
        us.saveUser("Sergey", "Popov", (byte) 59);
        us.getAllUsers().forEach(System.out::println);
        us.cleanUsersTable();
        us.dropUsersTable();
        Util.getSessionFactory().close();

        // реализуйте алгоритм здесь
    }
}
