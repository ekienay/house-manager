package service;

import dao.PersonDAO;
import entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    private final SessionFactory factory;


    public PersonDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }
    @Override
    public void create(Person person) {
        try(Session session = factory.openSession()){
           session.beginTransaction();
           session.save(person);
           session.getTransaction().commit();
           session.close();
        }
    }
    @Override
    public void update(Person person) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(person);
            session.getTransaction().commit();
            session.close();
        }

    }
    @Override
    public void delete(Person person) {
        try(Session session = factory.openSession()) {
            if (person!= null){
                session.beginTransaction();
                session.delete(person);
                session.getTransaction().commit();
                session.close();
            }
        }

    }
    @Override
    @SuppressWarnings("unchecked")
    public List<Person> findByAll() {
        try(Session session = factory.openSession()){
            session.beginTransaction();
        List<Person> personList = session.createQuery("select per from Person per inner join per.passport").list();
        session.getTransaction().commit();
        session.close();
        return personList;
        }
    }
}
