package service;

import dao.UserDAO;
import entity.Person;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final SessionFactory factory;


    public UserDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(User user) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = factory.openSession()) {
            if (user != null) {
                session.beginTransaction();
                session.delete(user);
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findByAll() {
        try (Session session = factory.openSession()) {
            List<User> userList = session.createQuery("From User").list();
            session.close();
            return userList;
        }
    }
}