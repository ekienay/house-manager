package dao;

import entity.Passport;
import entity.Person;

import java.util.List;

public interface PassportDAO {
    public void create(Passport passport);
    public void update (Passport passport);
    public void delete(Passport passport);
    Passport read(Integer integer);
    List<Passport> findByAll();
}
