package dev.maurer.services;

import dev.maurer.daos.ExpenseDAO;
import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.Manager;
import dev.maurer.exceptions.ExpenseNotFoundException;
import dev.maurer.exceptions.SubmissionException;
import dev.maurer.exceptions.SubmissionFormatException;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    @Override
    public Expense submitExpense(Employee employee, Expense expense) throws SubmissionException, SubmissionFormatException {
        if(expense.getReasonForExpense() == null || expense.getAmount() == null)
            throw new SubmissionFormatException("One or more required fields is empty");

        expense = expenseDAO.submitExpense(employee,expense);
        if (expense == null)
            throw new SubmissionException("Could not submit expense");

        return expense;
    }

    @Override
    public Expense viewExpense(Employee employee, int expenseId) throws ExpenseNotFoundException {
        Expense expense = expenseDAO.viewExpense(employee, expenseId);
        if (expense == null)
            throw new ExpenseNotFoundException(expenseId);
        return expense;
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
    public Expense updateExpenseStatus(Manager manager, Expense expense) throws ExpenseNotFoundException{
        Expense expense1 = expenseDAO.updateExpenseStatus(manager,expense);
        System.out.println(expense1);
        return expense1;
    }
}
