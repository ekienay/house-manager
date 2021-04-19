package dao;


import entity.Person;

import java.util.List;

public interface PersonDAO {

    public void create(Person person);
    public void update (Person person);
    public void delete(Person person);
    List<Person> findByAll();
}
