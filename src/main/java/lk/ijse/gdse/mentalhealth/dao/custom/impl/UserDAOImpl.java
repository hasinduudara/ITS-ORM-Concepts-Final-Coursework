package lk.ijse.gdse.mentalhealth.dao.custom.impl;

import lk.ijse.gdse.mentalhealth.config.FactoryConfiguration;
import lk.ijse.gdse.mentalhealth.dao.custom.UserDAO;
import lk.ijse.gdse.mentalhealth.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public User getUserByUsername(String userName) {
        try (Session session = factoryConfiguration.getSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", userName)
                    .uniqueResult();
        }
    }

    @Override
    public ArrayList<String> getAllRolls() {
        return null;
    }

    @Override
    public User findByRoll(String selectedId) throws Exception {
        return null;
    }

    @Override
    public boolean save(User entity) {
        Transaction transaction = null;
        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public String getNextId() {
        try (Session session = factoryConfiguration.getSession()) {
            // Get the last user ID from the database
            String lastId = session.createQuery("select u.id from User u order by u.id desc", String.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
                return String.format("U00-%03d", numericPart);
            } else {
                return "U00-001"; // First User ID
            }
        }
    }
}
