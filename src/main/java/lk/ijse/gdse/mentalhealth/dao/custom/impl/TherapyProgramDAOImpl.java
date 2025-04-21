package lk.ijse.gdse.mentalhealth.dao.custom.impl;

import lk.ijse.gdse.mentalhealth.bo.exception.DuplicateException;
import lk.ijse.gdse.mentalhealth.config.FactoryConfiguration;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.mentalhealth.entity.Therapist;
import lk.ijse.gdse.mentalhealth.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public boolean save(TherapyProgram entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Therapist existsTherapist = session.get(Therapist.class, entity.getProgramId());
            if (existsTherapist != null) {
                throw new DuplicateException("Therapist id duplicated");
            }
            session.persist(entity);
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
    public boolean update(TherapyProgram entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(entity); // update
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
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
            if (therapyProgram == null) {
                throw new Exception("Therapy Program not found");
            }
            session.remove(therapyProgram);
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
    public List<TherapyProgram> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<TherapyProgram> query = session.createQuery("FROM TherapyProgram", TherapyProgram.class);
        ArrayList<TherapyProgram> therapyPrograms = (ArrayList<TherapyProgram>) query.list();
        return therapyPrograms;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT tp.id FROM TherapyProgram tp ORDER BY tp.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
            return String.format("TP00-%03d", numericPart);
        } else {
            return "TP00-001"; // Default ID if no records exist
        }
    }

    @Override
    public TherapyProgram findById(String id) {
        Session session = factoryConfiguration.getSession();
        TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
        return therapyProgram;
    }
}
