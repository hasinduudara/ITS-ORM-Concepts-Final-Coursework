package lk.ijse.gdse.mentalhealth.dao.custom.impl;

import lk.ijse.gdse.mentalhealth.config.FactoryConfiguration;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.mentalhealth.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.sql.ast.tree.from.TableReference;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public TherapySession findById(String sessionId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = (Transaction) session.get(TherapySession.class, sessionId);
        session.close();
        return (TherapySession) transaction;
    }

    @Override
    public TherapySession getSessionByPatientName(String name, String date, String time) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<TherapySession> query = session.createQuery(
                    "FROM TherapySession ts WHERE ts.patient.name = :name ORDER BY ts.sessionDate DESC",
                    TherapySession.class);
            query.setParameter("name", name);
            query.setMaxResults(1); // Most recent session

            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    @Override
    public List<TherapySession> findByTherapistAndTime(String therapistId, LocalDate date, LocalTime time) {
        Session session = factoryConfiguration.getSession();
        Query<TherapySession> query = session.createQuery(
                "FROM TherapySession ts WHERE ts.therapist.id = :therapistId AND ts.sessionDate = :date AND ts.sessionTime = :time",
                TherapySession.class);

        query.setParameter("therapistId", therapistId);
        query.setParameter("date", date);
        query.setParameter("time", time);

        List<TherapySession> results = query.getResultList();
        session.close();
        return results;
    }

    @Override
    public TherapySession getSessionByPatientName(String name) {
        return null;
    }

    @Override
    public boolean save(TherapySession entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(TherapySession entity) {
        return false;
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<TherapySession> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<TherapySession> query = session.createQuery("FROM TherapySession", TherapySession.class);
        ArrayList<TherapySession> sessions = (ArrayList<TherapySession>) query.list();
        return sessions;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT sessionId FROM TherapySession ORDER BY sessionId DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("_")[1]) + 1;
            return String.format("TS00-%03d", numericPart);
        } else {
            return "TS00-001"; // Default ID if no records exist
        }
    }
}
