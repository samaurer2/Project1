package dev.maurer.entities;

import javax.persistence.*;

@Entity
@Table(name="expense")
public class Expense {

    @Id
    @Column(name="expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int expenseId;

    @Column(name = "amount")
    double amount;

    @Column(name = "expense_reason")
    String reasonForExpense;

    @Column(name = "date_submitted")
    long dateSubmitted;

    @Column(name ="expense_status")
    @Enumerated(EnumType.STRING)
    ExpenseStatus expenseStatus;

    @Column(name = "date_approved")
    long dateApprovedDenied;

    @Column(name = "approval_reason")
    String reasonForApprovalDenial;

    @ManyToOne()
    Employee employee;

    public Expense() {
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReasonForExpense() {
        return reasonForExpense;
    }

    public void setReasonForExpense(String reasonForExpense) {
        this.reasonForExpense = reasonForExpense;
    }

    public long getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(long dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public ExpenseStatus getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(ExpenseStatus expenseStatus) {
        this.expenseStatus = expenseStatus;
    }

    public long getDateApprovedDenied() {
        return dateApprovedDenied;
    }

    public void setDateApprovedDenied(long dateApprovedDenied) {
        this.dateApprovedDenied = dateApprovedDenied;
    }

    public String getReasonForApprovalDenial() {
        return reasonForApprovalDenial;
    }

    public void setReasonForApprovalDenial(String reasonForApprovalDenial) {
        this.reasonForApprovalDenial = reasonForApprovalDenial;
    }

    public Employee employee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", amount=" + amount +
                ", reasonForExpense='" + reasonForExpense + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", expenseStatus=" + expenseStatus +
                ", dateApprovedDenied=" + dateApprovedDenied +
                ", reasonForApprovalDenial='" + reasonForApprovalDenial + '\'' +
                ", employee=" + employee +
                '}';
    }
}
