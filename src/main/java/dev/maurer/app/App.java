package dev.maurer.app;

import dev.maurer.controllers.EmployeeController;
import dev.maurer.controllers.ExpenseController;
import dev.maurer.daos.EmployeeDAO;
import dev.maurer.daos.ExpenseDAO;
import dev.maurer.daos.HibernateEmployeeDAO;
import dev.maurer.daos.HibernateExpenseDao;
import dev.maurer.services.EmployeeService;
import dev.maurer.services.EmployeeServiceImpl;
import dev.maurer.services.ExpenseService;
import dev.maurer.services.ExpenseServiceImpl;
import dev.maurer.utils.HibernateUtil;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        EmployeeDAO employeeDAO = new HibernateEmployeeDAO(HibernateUtil.getSessionFactory(false));
        EmployeeService employeeService = new EmployeeServiceImpl(employeeDAO);
        EmployeeController employeeController = new EmployeeController(employeeService);

        ExpenseDAO expenseDAO = new HibernateExpenseDao(employeeDAO);
        ExpenseService expenseService = new ExpenseServiceImpl(expenseDAO);
        ExpenseController expenseController = new ExpenseController(expenseService, employeeService);

        app.post("/login", employeeController.loginHandler);
        app.post("/expense", expenseController.submitExpenseHandler);

        app.get("/expense",expenseController.getExpensesHandler);
        app.get("/expense/:id", expenseController.getExpenseByIdHandler);

        app.put("/expense/:id", expenseController.updateExpenseHandler);

        app.start();
    }
}
