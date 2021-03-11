package dev.maurer.daos;

import dev.maurer.entities.Employee;
import dev.maurer.entities.Expense;
import dev.maurer.entities.Manager;

import java.util.List;

public interface ExpenseDAO {

    public Expense submitExpense(Employee employee, Expense expense);

    public Expense viewExpense(Employee employee, int expenseId);

    public List<Expense> viewAllExpenses(Employee employee);
    public List<Expense> viewAllExpenses(Manager manager);

    public Expense updateExpenseStatus(Manager manager, Expense newExpense);

}
