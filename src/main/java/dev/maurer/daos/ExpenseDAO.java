package dev.maurer.daos;

import dev.maurer.entities.Expense;

import java.util.Set;

public interface ExpenseDAO {

    public Set<Expense> getEmployeeExpenses();
}
