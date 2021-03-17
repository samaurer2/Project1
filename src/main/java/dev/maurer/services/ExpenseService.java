package dev.maurer.services;

import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.Manager;
import dev.maurer.exceptions.ExpenseNotFoundException;
import dev.maurer.exceptions.SubmissionException;
import dev.maurer.exceptions.SubmissionFormatException;

import java.util.List;

public interface ExpenseService {

    public Expense submitExpense(Employee employee, Expense expense) throws SubmissionException, SubmissionFormatException;

    public Expense viewExpense(Employee employee, int expenseId) throws ExpenseNotFoundException;

    public List<Expense> viewAllExpenses(Employee employee);

    public List<Expense> viewAllExpenses(Manager manager);

    public Expense updateExpenseStatus(Manager manager, Expense expense) throws ExpenseNotFoundException;
}
