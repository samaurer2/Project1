package dev.maurer.app;

import dev.maurer.controllers.EmployeeController;
import dev.maurer.daos.EmployeeDAO;
import dev.maurer.daos.HibernateEmployeeDAO;
import dev.maurer.services.EmployeeService;
import dev.maurer.services.EmployeeServiceImpl;
import dev.maurer.utils.HibernateUtil;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        EmployeeDAO employeeDAO = new HibernateEmployeeDAO(HibernateUtil.getSessionFactory(false));
        EmployeeService employeeService = new EmployeeServiceImpl(employeeDAO);
        EmployeeController employeeController = new EmployeeController(employeeService);

        //ExpenseController expenseController = new ExpenseController();
        app.put("/login", employeeController.loginHandler);


        app.start();
    }
}
