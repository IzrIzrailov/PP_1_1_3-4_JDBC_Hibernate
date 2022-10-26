package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users 
                    (`id` BIGINT PRIMARY KEY AUTO_INCREMENT, 
                    `name` VARCHAR(32), 
                    `last_name` VARCHAR(32), 
                    `age` TINYINT);
                """;
        Transaction transaction = null;

        try (Session session = Util.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(createUsersTable).executeUpdate();
            System.out.println("Таблица users создана");
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Таблица users ну удалось создать");
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        String dropUsersTable = "DROP TABLE IF EXISTS users";
        Transaction transaction = null;

        try (Session session = Util.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(dropUsersTable).executeUpdate();
            System.out.println("Таблица users удалена");
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Таблица users не удалось удалить");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("User с именем – " + name + " не удалост добавить в базу данных");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            System.out.println("Пользователь с id = " + id + " удален");
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Пользователя с id = " + id + " не удалось удалить");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = Util.getSession().openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            transaction = session.beginTransaction();
            userList = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Не удалось получить всех пользователей");
            e.printStackTrace();
        }
        System.out.println("Все пользователи: ");
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String cleanUsersTable = "TRUNCATE users";
        Transaction transaction = null;

        try (Session session = Util.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(cleanUsersTable).executeUpdate();
            System.out.println("Таблица очищена");
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Таблицу не удалось очистить");
            e.printStackTrace();
        }
    }
}
