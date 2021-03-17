package dev.maurer.ServiceTests;

import dev.maurer.daos.EmployeeDAO;
import dev.maurer.daos.HibernateEmployeeDAO;
import dev.maurer.entities.Employee;
import dev.maurer.entities.EmployeeType;
import dev.maurer.entities.Manager;
import dev.maurer.exceptions.EmployeeNotFoundException;
import dev.maurer.exceptions.UserLoginException;
import dev.maurer.services.EmployeeService;
import dev.maurer.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    private static EmployeeService employeeService;
    private static Employee testEmployee;
    private static Manager testManager;
    private static Employee fakeEmployee;

    @Mock
    private static EmployeeDAO employeeDAO = Mockito.mock(HibernateEmployeeDAO.class);


    @BeforeAll
    static void setUp() {
        Employee testEmployee = new Employee("Annie", "The Dark Child");
        testEmployee.setEmployeeId(1);
        testEmployee.setType(EmployeeType.EMPLOYEE);

        Manager testManager = new Manager("Dr. Mundo", "The Madman of Zaun");
        testManager.setEmployeeId(2);
        testManager.setType(EmployeeType.MANAGER);
        employeeService = new EmployeeServiceImpl(employeeDAO);

        Mockito.when(employeeDAO.login(argThat(new EmployeeMatcher()))).thenReturn(testEmployee);
        Mockito.when(employeeDAO.login(any(Manager.class))).thenReturn(testManager);
        Mockito.when(employeeDAO.getEmployeeById(1)).thenReturn(testEmployee);
        Mockito.when(employeeDAO.getEmployeeById(2)).thenReturn(testManager);
        Mockito.when(employeeDAO.getEmployeeById(100)).thenReturn(null);
        Mockito.when(employeeDAO.login(fakeEmployee)).thenReturn(null);
    }

    @Test
    void employeeServiceEmployeeLoginTest() {
        try {
            Employee employee = new Employee("Annie", "The Dark Child");
            employee = employeeService.login(employee);
            Assertions.assertNotEquals(0, employee.getEmployeeId());
        } catch (Exception e) {

        }
    }

    @Test
    void employeeServiceManagerLoginTest() {
        try {
            Employee manager = new Manager("Dr. Mundo", "The Madman of Zaun");
            manager = employeeService.login(manager);
            Assertions.assertNotEquals(0, manager.getEmployeeId());
        } catch (Exception e) {

        }
    }

    @Test
    void getEmployeeByIdTest() {
        try {
            Employee employee = employeeService.getEmployeeById(1);
            Assertions.assertTrue(employee.getEmployeeId() == 1);
            Assertions.assertFalse(employee instanceof Manager);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void getManagerByIdTest() {
        try {
            Employee employee = employeeService.getEmployeeById(2);
            Assertions.assertTrue(employee.getEmployeeId() == 2);
            Assertions.assertTrue(employee instanceof Manager);
        } catch (Exception e) {
            Assertions.fail();

        }
    }

    @Test
    void getEmployeeByIdExceptionTest() {
        try {
            Employee employee = employeeService.getEmployeeById(100);
            Assertions.fail();
        } catch (EmployeeNotFoundException e) {
        } catch (Exception e) {
            Assertions.fail();

        }
    }

    @Test
    void employeeLoginExceptionTest() {
        try {
            employeeService.login(fakeEmployee);
            Assertions.fail();
        } catch (UserLoginException e) {

        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
