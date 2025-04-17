package lk.ijse.gdse.mentalhealth.dao.custom;

import lk.ijse.gdse.mentalhealth.entity.User;

public interface UserDAO extends CrudDAO <User> {
    User getUserByUsername(String userName);
}
