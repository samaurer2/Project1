package dev.maurer.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sf;

    public static SessionFactory getSessionFactory(boolean testing) {
        if (sf == null) {
            Configuration cfg = new Configuration();
            if (testing)
                sf = cfg.configure("test.cfg.xml").buildSessionFactory();
            else
                sf = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sf;
    }

    public static void closeFactory() {
        sf = null;
    }
}
