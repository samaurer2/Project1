package dev.maurer.ServiceTests;

import dev.maurer.daos.EmployeeDAO;
import dev.maurer.daos.HibernateEmployeeDAO;
import dev.maurer.entities.Employee;
import dev.maurer.entities.EmployeeType;
import dev.maurer.entities.Manager;
import dev.maurer.services.EmployeeService;
import dev.maurer.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    private static EmployeeService employeeService;
    private static Employee testEmployee;
    private static Manager testManager;

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
}
