package dev.maurer.services;

import dev.maurer.daos.ExpenseDAO;
import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.Manager;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    @Override
    public Expense submitExpense(Employee employee, Expense expense) {
        return expenseDAO.submitExpense(employee, expense);
    }

    @Override
    public Expense viewExpense(Employee employee, int expenseId) {
        return expenseDAO.viewExpense(employee, expenseId);
    }

    @Override
    public List<Expense> viewAllExpenses(Employee employee) {
        return expenseDAO.viewAllExpenses(employee);
    }

    @Override
    public List<Expense> viewAllExpenses(Manager manager) {
        return expenseDAO.viewAllExpenses(manager);
    }

    @Override
    public Expense updateExpenseStatus(Manager manager, Expense expense) {
        return expenseDAO.updateExpenseStatus(manager,expense);
    }
}
