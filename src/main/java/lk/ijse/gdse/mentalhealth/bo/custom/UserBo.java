package lk.ijse.gdse.mentalhealth.bo.custom;

import lk.ijse.gdse.mentalhealth.entity.User;
import lk.ijse.gdse.mentalhealth.util.Role;

import java.util.ArrayList;

public interface UserBo {
    boolean registerUser(String name, String userName, String email, String password, Role role);

    ArrayList<String> getAllRoll();

    User findByRoll (String selectedRoll) throws Exception;
}
