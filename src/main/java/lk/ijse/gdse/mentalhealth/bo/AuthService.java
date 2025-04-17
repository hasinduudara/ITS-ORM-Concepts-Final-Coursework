package lk.ijse.gdse.mentalhealth.bo;

import lk.ijse.gdse.mentalhealth.dao.custom.UserDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.UserDAOImpl;
import lk.ijse.gdse.mentalhealth.entity.User;
import lk.ijse.gdse.mentalhealth.util.PasswordEncryptionUtil;
import lk.ijse.gdse.mentalhealth.util.Role;

public class AuthService {
    private final UserDAO userDAO = new UserDAOImpl();

    public Role authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && PasswordEncryptionUtil.checkPassword(password, user.getPassword())) {
            return user.getRole();
        }
        return null;
    }
}
