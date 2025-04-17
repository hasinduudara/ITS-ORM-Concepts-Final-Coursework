package lk.ijse.gdse.mentalhealth.bo.custom.impl;

import lk.ijse.gdse.mentalhealth.bo.custom.UserBo;
import lk.ijse.gdse.mentalhealth.dao.custom.UserDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.UserDAOImpl;
import lk.ijse.gdse.mentalhealth.entity.User;
import lk.ijse.gdse.mentalhealth.util.PasswordEncryptionUtil;
import lk.ijse.gdse.mentalhealth.util.Role;

import java.util.ArrayList;

public class UserBOImpl implements UserBo{
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean registerUser(String name, String userName, String email, String password, Role role) {
        if (userDAO.getUserByUsername(userName) != null) {
            return false; // User already exists
        }

        String hashedPassword = PasswordEncryptionUtil.hashPassword(password);
        String newUserId = userDAO.getNextId(); // Generate new ID
        User user = new User(newUserId, name, userName, email, hashedPassword, role);
        userDAO.save(user);
        return true;
    }

    @Override
    public ArrayList<String> getAllRoll() {
        return userDAO.getAllRolls();
    }

    @Override
    public User findByRoll(String selectedRoll) throws Exception {
        return userDAO.findByRoll(selectedRoll);
    }
}
