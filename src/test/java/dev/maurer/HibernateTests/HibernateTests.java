package dev.maurer.HibernateTests;

import dev.maurer.entities.Employee;
import dev.maurer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class HibernateTests {


    @Test
    void loginHibernateTest(){
        SessionFactory sf = HibernateUtil.getSessionFactory(true);
        Session session = sf.openSession();
        session.getTransaction().begin();

        Employee employee = new Employee();
        employee.setName("Annie");
        employee.setPassword("The Dark Child");

    }
}
