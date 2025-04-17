package lk.ijse.gdse.mentalhealth.dao.custom;

import lk.ijse.gdse.mentalhealth.entity.User;

import java.util.ArrayList;

public interface UserDAO extends CrudDAO <User> {
    User getUserByUsername(String userName);

    ArrayList<String> getAllRolls();

    User findByRoll(String selectedId) throws Exception;
}
