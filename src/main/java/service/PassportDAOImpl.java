package service;

import dao.PassportDAO;
import entity.Passport;
import entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PassportDAOImpl implements PassportDAO {

    private final SessionFactory factory;

    public PassportDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public void create(Passport passport) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(passport);
            session.getTransaction().commit();
            session.close();
        }

    }

    @Override
    public void update(Passport passport) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(passport);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void delete(Passport passport) {
        try (Session session = factory.openSession()) {
            if (passport != null) {
                session.beginTransaction();
                session.delete(passport);
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    @Override
    public Passport read(Integer integer) {
        try(Session session = factory.openSession()){
          return session.get(Passport.class, integer);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Passport> findByAll() {
            try(Session session = factory.openSession()){
                List<Passport> passportList = session.createQuery("From Passport").list();
                session.close();
                return passportList;
            }
    }
}
