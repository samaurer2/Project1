package dev.maurer.ServiceTests;

import dev.maurer.daos.EmployeeDAO;
import dev.maurer.daos.ExpenseDAO;
import dev.maurer.daos.HibernateEmployeeDAO;
import dev.maurer.daos.HibernateExpenseDao;
import dev.maurer.entities.*;
import dev.maurer.exceptions.ExpenseNotFoundException;
import dev.maurer.services.ExpenseServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseServiceTests {

    @Mock
    private static EmployeeDAO employeeDAO = Mockito.mock(HibernateEmployeeDAO.class);

    @Mock
    private static ExpenseDAO expenseDAO = Mockito.mock(HibernateExpenseDao.class);
    private static ExpenseServiceImpl expenseService;

    private static Employee testEmployee;
    private static Manager testManager;
    private static List<Expense> managerList;
    private static List<Expense> employeeList;

    @BeforeAll
    static void setUp() {
        Employee testEmployee = new Employee("Annie", "The Dark Child");
        testEmployee.setEmployeeId(1);
        testEmployee.setType(EmployeeType.EMPLOYEE);

        Manager testManager = new Manager("Dr. Mundo", "The Madman of Zaun");
        testManager.setEmployeeId(2);
        testManager.setType(EmployeeType.MANAGER);

        Employee otherEmployee = new Employee("Teemo", "The Swift Scout");
        otherEmployee.setEmployeeId(3);
        otherEmployee.setType(EmployeeType.EMPLOYEE);

        Mockito.when(employeeDAO.login(argThat(new EmployeeMatcher()))).thenReturn(testEmployee);
        Mockito.when(employeeDAO.login(any(Manager.class))).thenReturn(testManager);

        Expense expense1 = new Expense(10.00, "Dummy Data");
        expense1.setExpenseId(1);
        expense1.setEmployee(testEmployee);

        Expense expense2 = new Expense(20.00, "Dummy Data");
        expense2.setExpenseId(2);
        expense2.setEmployee(testEmployee);

        Expense expense3 = new Expense(30.00, "Dummy Data");
        expense3.setExpenseId(3);
        expense3.setEmployee(otherEmployee);

        employeeList = new ArrayList<>();
        employeeList.add(expense1);
        employeeList.add(expense2);

        managerList = new ArrayList<>();
        managerList.add(expense1);
        managerList.add(expense2);
        managerList.add(expense3);

        Expense testExpense = new Expense(100.00, "The big dummy data");
        testExpense.setExpenseId(4);
        testExpense.setEmployee(testEmployee);

        Mockito.when(expenseDAO.submitExpense(any(), any())).thenReturn(testExpense);
        Mockito.when(expenseDAO.viewExpense(argThat(new EmployeeMatcher()), eq(4))).thenReturn(testExpense);
        Mockito.when(expenseDAO.viewExpense(argThat(new EmployeeMatcher()), eq(100))).thenReturn(null);
        Mockito.when(expenseDAO.viewAllExpenses(any(Manager.class))).thenReturn(managerList);
        Mockito.when(expenseDAO.viewAllExpenses(argThat(new EmployeeMatcher()))).thenReturn(employeeList);
        Mockito.when(expenseDAO.updateExpenseStatus(any(Manager.class), any(Expense.class))).thenReturn(testExpense);
        expenseService = new ExpenseServiceImpl(expenseDAO);


    }

    @Test
    @Order(1)
    void submitExpenseServiceTest() {
        Expense expense = new Expense(100.00, "The big dummy data");
        Employee employee = new Employee("Annie", "The Dark Child");

        expense = expenseService.submitExpense(employee, expense);
        Assertions.assertNotNull(expense);
    }

    @Test
    @Order(2)
    void getAllExpensesEmployeeServiceTest() {
        Employee employee = new Employee("Annie", "The Dark Child");

        List<Expense> expenseList = expenseService.viewAllExpenses(employee);
        Assertions.assertEquals(2, expenseList.size());
    }

    @Test
    @Order(3)
    void getAllExpensesManagerServiceTest() {
        Manager manager = new Manager("Dr. Mundo", "The Madman of Zaun");

        List<Expense> expenseList = expenseService.viewAllExpenses(manager);
        Assertions.assertEquals(3, expenseList.size());
    }

    @Test
    @Order(4)
    void getExpenseServiceTest() {
        try {
            Employee employee = new Employee("Annie", "The Dark Child");
            Expense expense = expenseService.viewExpense(employee, 4);
            Assertions.assertNotNull(expense);
        } catch (Exception e) {
            Assertions.fail();
        }

    }

    @Test
    @Order(5)
    void updateExpenseServiceTest() {
        try {


            Manager manager = new Manager("Dr. Mundo", "The Madman of Zaun");
            Expense expense = new Expense();
            expense.setExpenseId(4);
            expense.setExpenseStatus(ExpenseStatus.APPROVED);
            expense = expenseService.updateExpenseStatus(manager, expense);
            Assertions.assertNotNull(expense);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @Order(6)
    void getExpenseByIdExceptionTest() {
        try {
            expenseService.viewExpense(testEmployee, 100);
            Assertions.fail();
        } catch (ExpenseNotFoundException e) {
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
