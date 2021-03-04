package dev.maurer.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sf;

    public static SessionFactory getSessionFactory(boolean testing) {
        if (sf == null) {
            Configuration cfg = new Configuration();
            if(testing)
                cfg.setProperty("hibernate.hbm2ddl.auto","create");
            sf = cfg.configure().buildSessionFactory();
        }
        return sf;
    }

}
