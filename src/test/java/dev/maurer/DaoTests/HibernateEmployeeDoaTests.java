package dev.maurer.DaoTests;

import dev.maurer.daos.EmployeeDAO;
import dev.maurer.daos.HibernateEmployeeDAO;
import dev.maurer.entities.Employee;
import dev.maurer.entities.EmployeeType;
import dev.maurer.entities.Manager;
import dev.maurer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HibernateEmployeeDoaTests {

    private static EmployeeDAO employeeDAO;

    @BeforeAll
    static void setUp(){
        SessionFactory sf = HibernateUtil.getSessionFactory(true);
        employeeDAO = new HibernateEmployeeDAO(sf);
        Employee e1 = new Employee();
        e1.setName("Annie");
        e1.setPassword("The Dark Child");

        Manager m1 = new Manager();
        m1.setName("Dr. Mundo");
        m1.setPassword("The Madman of Zaun");
        m1.setType(EmployeeType.MANAGER);
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(e1);
        session.save(m1);
        session.close();
    }
    @Test
    void loginEmployee(){
        Employee employee = new Employee();
        employee.setName("Annie");
        employee.setPassword("The Dark Child");
        employee.setEmployeeId(0);

        employee = employeeDAO.login(employee);
        Assertions.assertNotEquals(0, employee.getEmployeeId());
        Assertions.assertFalse(employee instanceof Manager);
    }

    @Test
    void loginManger(){
        Employee employee = new Employee();
        employee.setName("Dr. Mundo");
        employee.setPassword("The Madman of Zaun");
        employee.setEmployeeId(0);

        employee = employeeDAO.login(employee);
        Assertions.assertTrue(employee instanceof Manager);
    }

}
