package dev.maurer.daos;

import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.ExpenseStatus;
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
        Employee emp = employeeDAO.login(employee);
        try (Session session = sf.openSession()) {
            expense.setEmployee(emp);
            expense.setDateSubmitted(System.currentTimeMillis());
            expense.setExpenseStatus(ExpenseStatus.PENDING);
            session.beginTransaction();
            session.save(expense);
            return expense;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Expense viewExpense(Employee employee, int expenseId) {

        Employee emp = employeeDAO.login(employee);
        try (Session session = sf.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);
            if (emp instanceof Manager)
                criteriaQuery.select(root).where(builder.equal(root.get("expenseId"), expenseId));
            else
                criteriaQuery.select(root).where(builder.equal(root.get("employee"), emp)).where(builder.equal(root.get("expenseId"), expenseId));

            Query expenseQuery = session.createQuery(criteriaQuery);
            List<Expense> results = expenseQuery.getResultList();

            if (results.size() != 1)
                return null;
            return results.get(0);
        }

    }

    @Override
    public List<Expense> viewAllExpenses(Employee employee) {


        Employee emp = employeeDAO.login(employee);
        try (Session session = sf.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);
            criteriaQuery.select(root).where(builder.equal(root.get("employee"), emp));

            Query expenseQuery = session.createQuery(criteriaQuery);
            List<Expense> results = expenseQuery.getResultList();

            return results;
        }
    }

    @Override
    public List<Expense> viewAllExpenses(Manager manager) {

        Employee man = employeeDAO.login(manager);
        try (Session session = sf.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);
            criteriaQuery.select(root);

            Query expenseQuery = session.createQuery(criteriaQuery);
            List<Expense> results = expenseQuery.getResultList();

            return results;
        }
    }

    @Override
    public Expense updateExpenseStatus(Manager manager, Expense newExpense) {
        Employee man = employeeDAO.login(manager);
        try (Session session = sf.openSession()) {
            Expense expense = session.load(Expense.class, newExpense.getExpenseId());
            if (newExpense.getExpenseStatus() != ExpenseStatus.PENDING) {
                expense.setExpenseStatus(newExpense.getExpenseStatus());
                expense.setReasonForApprovalDenial(newExpense.getReasonForApprovalDenial());
                expense.setDateApprovedDenied(System.currentTimeMillis());
            }
            session.save(expense);
            return expense;
        }
    }
}
