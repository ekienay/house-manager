package dao;

import entity.Person;
import entity.User;

import java.util.List;

public interface UserDAO
{
    public void create(User user);
    public void update (User user);
    public void delete(User user);
    List<User> findByAll();
}
