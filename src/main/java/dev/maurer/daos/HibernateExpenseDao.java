package dev.maurer.daos;

import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.ExpenseStatus;
import dev.maurer.entities.Manager;
import dev.maurer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.*;
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
        if ((emp == null) || expense == null)
            return null;
        try (Session session = sf.openSession()) {
            expense.setEmployee(emp);
            expense.setDateSubmitted(System.currentTimeMillis());
            expense.setExpenseStatus(ExpenseStatus.PENDING);
            session.beginTransaction();
            session.save(expense);
            session.getTransaction().commit();
            return expense;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Expense viewExpense(Employee employee, int expenseId) {

        Employee emp = employeeDAO.login(employee);
        System.out.println(employee);
        if (emp == null)
            return null;
        try (Session session = sf.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);

            if (emp instanceof Manager)
                criteriaQuery.select(root).where(builder.equal(root.get("expenseId"), expenseId));
            else {
                Predicate p1 = builder.equal(root.get("employee"), emp.getEmployeeId());
                Predicate p2 = builder.equal(root.get("expenseId"), expenseId);
                Predicate p1Andp2 = builder.and(p1, p2);
                criteriaQuery.select(root).where(p1Andp2);
            }
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
        if (emp == null)
            return null;
        try (Session session = sf.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Expense> criteriaQuery = builder.createQuery(Expense.class);
            Root<Expense> root = criteriaQuery.from(Expense.class);
            criteriaQuery.select(root).where(builder.equal(root.get("employee"), emp));

            Query expenseQuery = session.createQuery(criteriaQuery);
            List<Expense> results= expenseQuery.getResultList();
            results.size();
            return results;
        }
    }

    @Override
    public List<Expense> viewAllExpenses(Manager manager) {

        Employee man = employeeDAO.login(manager);
        if (man == null)
            return null;
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
        if (man == null)
            return null;
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
