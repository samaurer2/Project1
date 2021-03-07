package dev.maurer.exceptions;

public class ExpenseNotFoundException extends Exception{

    public ExpenseNotFoundException(int expenseId){
        super("Expense " + expenseId + "not found");
    }
}
