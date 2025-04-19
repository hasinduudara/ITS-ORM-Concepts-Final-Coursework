package lk.ijse.gdse.mentalhealth.dao.custom.impl;

import jakarta.persistence.Query;
import lk.ijse.gdse.mentalhealth.bo.exception.DuplicateException;
import lk.ijse.gdse.mentalhealth.config.FactoryConfiguration;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapistDAO;
import lk.ijse.gdse.mentalhealth.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public Therapist getById(String therapistId) {
        try {
            Session session = factoryConfiguration.getSession();
            Therapist therapist = session.get(Therapist.class, therapistId);
            return therapist;
        } catch (Exception e) {
            throw new RuntimeException("Therapist not found", e);
        }
    }

    @Override
    public boolean save(Therapist entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // Check if the therapist already exists
            Therapist existingTherapist = session.get(Therapist.class, entity.getTherapistID());
            if (existingTherapist != null) {
                throw new DuplicateException("Therapist ID already exists");
            }

            session.persist(entity); // Save the new therapist
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Therapist entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity); // Update the therapist
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Therapist therapist = session.get(Therapist.class, id);
            if (therapist != null) {
                session.delete(therapist); // Delete the therapist
                transaction.commit();
                return true;
            } else {
                throw new Exception("Therapist not found");
            }
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Therapist> getAll() {
        Session session = factoryConfiguration.getSession();
        Query query = session.createQuery("FROM Therapist", Therapist.class);
        ArrayList<Therapist> therapists = (ArrayList<Therapist>) ((org.hibernate.query.Query<?>) query).list();
        return therapists;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        // Get the last therapist ID from the database
        String lastId = session.createQuery("SELECT t.id FROM Therapist t ORDER BY t.id DESC", String.class)
                .setMaxResults(1)
                .getSingleResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1; // Assuming ID format is "T001", "T002", etc.
            return String.format("T00-%03d", numericPart); // Format the new ID as "T00X"
        } else {
            return "T00-001"; // If no therapists exist, start with the first ID
        }
    }
}
