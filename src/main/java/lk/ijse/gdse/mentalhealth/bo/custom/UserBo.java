package lk.ijse.gdse.mentalhealth.bo.custom;

import lk.ijse.gdse.mentalhealth.util.Role;

public interface UserBo {
    boolean registerUser(String name, String userName, String email, String password, Role role);
}
