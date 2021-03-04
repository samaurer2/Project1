package dev.maurer.daos;

import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.Manager;
import dev.maurer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateExpenseDao implements ExpenseDAO {

    private EmployeeDAO employeeDAO;
    private SessionFactory sf;

    public HibernateExpenseDao(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
        sf = HibernateUtil.getSessionFactory(false);
    }

    @Override
    public Expense submitExpense(Employee employee, Expense expense) {
        return null;
    }

    @Override
    public Expense viewExpense(Employee employee, int expenseId) {
        Session session = null;
        try {
            Employee emp = employeeDAO.login(employee);
            session = sf.openSession(); //session is closed upon return from login

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);
            criteriaQuery.select(root).where(builder.equal(root.get("employee"), emp)).where(builder.equal(root.get("expenseId"),expenseId));

            Query expenseQuery = session.createQuery(criteriaQuery);
            List<Expense> results = expenseQuery.getResultList();

            if (results.size()!=1)
                return null;
            return results.get(0);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Expense> viewAllExpenses(Employee employee) {

        Session session = null;
        try {
            Employee emp = employeeDAO.login(employee);
            session = sf.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);
            criteriaQuery.select(root).where(builder.equal(root.get("employee"), emp));
            Query expenseQuery = session.createQuery(criteriaQuery);
            List<Expense> results = expenseQuery.getResultList();
            return results;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Expense> viewAllExpenses(Manager manager) {
        Session session = null;
        try {
            Employee man = employeeDAO.login(manager);
            session = sf.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);
            criteriaQuery.select(root);
            Query expenseQuery = session.createQuery(criteriaQuery);
            List<Expense> results = expenseQuery.getResultList();
            return results;
        } finally {
            session.close();
        }
    }

    @Override
    public Expense updateExpenseStatus(Manager manager, Expense expense) {
        return null;
    }
}
