package lk.ijse.gdse.mentalhealth.config;

import lk.ijse.gdse.mentalhealth.entity.Patient;
import lk.ijse.gdse.mentalhealth.entity.Therapist;
import lk.ijse.gdse.mentalhealth.entity.TherapyProgram;
import lk.ijse.gdse.mentalhealth.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    public static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;
    public FactoryConfiguration(){
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        configuration.setProperties(properties);

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Therapist.class);
        configuration.addAnnotatedClass(TherapyProgram.class);
        configuration.addAnnotatedClass(Patient.class);

        sessionFactory = configuration.buildSessionFactory();
    }
    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? new FactoryConfiguration() : factoryConfiguration;
    }
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
