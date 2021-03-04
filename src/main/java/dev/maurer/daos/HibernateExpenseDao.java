package dev.maurer.daos;

import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.Manager;

import java.util.List;

public class PostgresExpenseDao implements ExpenseDAO{

    private EmployeeDAO employeeDAO;

    public PostgresExpenseDao(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    @Override
    public Expense submitExpense(Employee employee, Expense expense) {
        return null;
    }

    @Override
    public Expense viewExpense(Employee employee, int expenseId) {
        return null;
    }

    @Override
    public List<Expense> viewAllExpenses(Employee employee) {
        return null;
    }

    @Override
    public List<Expense> viewAllExpenses(Manager manager) {
        return null;
    }

    @Override
    public Expense updateExpenseStatus(Manager manager, Expense expense) {
        return null;
    }
}
