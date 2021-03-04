package dev.maurer.DaoTests;


import dev.maurer.daos.EmployeeDAO;
import dev.maurer.daos.ExpenseDAO;
import dev.maurer.daos.HibernateEmployeeDAO;
import dev.maurer.daos.HibernateExpenseDao;
import dev.maurer.entities.Employee;
import dev.maurer.entities.EmployeeType;
import dev.maurer.entities.Expense;
import dev.maurer.entities.Manager;
import dev.maurer.utils.ConnectionUtil;
import dev.maurer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HibernateExpenseDaoTests {


    private static SessionFactory sf;
    private static Session session;
    private static EmployeeDAO employeeDAO;
    private static ExpenseDAO expenseDAO;

    @BeforeAll
    static void setUp() {
        sf = HibernateUtil.getSessionFactory(true);
        session = sf.openSession();
        Employee e1 = new Employee();
        e1.setName("Annie");
        e1.setPassword("The Dark Child");

        Employee e2 = new Employee();
        e2.setName("Teemo");
        e2.setPassword("The Swift Scout");

        Manager m1 = new Manager();
        m1.setName("Dr. Mundo");
        m1.setPassword("The Madman of Zaun");
        m1.setType(EmployeeType.MANAGER);

        Expense exp1 = new Expense();
        exp1.setEmployee(e1);
        exp1.setAmount(20.00);
        exp1.setReasonForExpense("Need to sew up hole in Tibbers");

        Expense exp2 = new Expense();
        exp2.setEmployee(e2);
        exp2.setAmount(50.00);
        exp2.setReasonForExpense("Need a bigger backpack told hold mushrooms");

        Expense exp3 = new Expense();
        exp3.setEmployee(e2);
        exp3.setAmount(200.00);
        exp3.setReasonForExpense("Need even bigger backpack to hold more mushrooms");

        session.beginTransaction();
        session.save(e1);
        session.save(e2);
        session.save(m1);
        session.save(exp1);
        session.save(exp2);
        session.save(exp3);
        session.close();
        employeeDAO = new HibernateEmployeeDAO(sf);
        expenseDAO = new HibernateExpenseDao(employeeDAO);

    }

    @AfterAll
    static void teardown() {
        session.close();
        sf.close();
    }

    @Test
    @Order(1)
    void getExpensesEmployeeTest() {
        Employee employee = new Employee("Teemo","The Swift Scout");
        List<Expense> expenses = expenseDAO.viewAllExpenses(employee);
        Assertions.assertTrue(expenses.size() == 2);
    }

    @Test
    @Order(2)
    void getExpensesManagerTest() {
        Manager manager = new Manager("Dr. Mundo", "The Madman of Zaun");
        List<Expense> expenses = expenseDAO.viewAllExpenses(manager);
        Assertions.assertTrue(expenses.size()==3);
    }

    @Test
    @Order(3)
    void getExpense() {
        Employee employee = new Employee("Teemo", "The Swift Scout");
        Expense expense = expenseDAO.viewExpense(employee, 2);
        Assertions.assertTrue(expense.getExpenseId() == 2);
    }

    @Test
    @Order(4)
    void test4() {
        Assertions.fail();
    }

    @Test
    @Order(5)
    void test5() {
        Assertions.fail();
    }

    @Test
    @Order(6)
    void test6() {
        Assertions.fail();

    }

}
