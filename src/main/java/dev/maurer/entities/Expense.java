package dev.maurer.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name="expense")
public class Expense implements Comparable{

    @Id
    @Column(name="expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "expense_reason", nullable = false)
    private String reasonForExpense;

    @Column(name = "date_submitted")
    private Long dateSubmitted;

    @Column(name ="expense_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseStatus expenseStatus;

    @Column(name = "date_approved")
    private Long dateApprovedDenied;

    @Column(name = "approval_reason")
    private String reasonForApprovalDenial;

    @ManyToOne
    private Employee employee;

    public Expense() {
        this.expenseStatus = ExpenseStatus.PENDING;
    }

    public Expense(double amount, String reasonForExpense) {
        this.amount = amount;
        this.reasonForExpense = reasonForExpense;
        this.expenseStatus = ExpenseStatus.PENDING;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public Double getAmount() {
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

    public Long getDateSubmitted() {
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

    public Long getDateApprovedDenied() {
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

    @Override
    public int compareTo(@NotNull Object object) {
        Expense expense = (Expense) object;

        if (this.expenseStatus.ordinal() > expense.getExpenseStatus().ordinal())
            return 1;

        if (this.expenseStatus.ordinal() < expense.getExpenseStatus().ordinal())
            return -1;

        if(this.dateSubmitted > expense.getDateSubmitted())
            return -1;

        if(this.dateSubmitted < expense.getDateSubmitted())
            return 1;

        return 0;
    }
}
