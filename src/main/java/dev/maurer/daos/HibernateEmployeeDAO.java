package dev.maurer.daos;

import dev.maurer.entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateEmployeeDAO implements EmployeeDAO {

    private SessionFactory sf;

    public HibernateEmployeeDAO(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Employee login(Employee employee) {

        try (Session session = sf.openSession()) {
            CriteriaBuilder builder = sf.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            criteriaQuery.select(root)
                    .where(builder.equal(root.get("name"), employee.getName()))
                    .where(builder.equal(root.get("password"), employee.getPassword()));

            Query query = session.createQuery(criteriaQuery);
            List<Employee> results = query.getResultList();

            if (results.size() != 1)
                return null;
            else {
                results.get(0).setExpenses(null);
                return results.get(0);
            }
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        try(Session session = sf.openSession()) {

            CriteriaBuilder builder = sf.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(root).where(builder.equal(root.get("employeeId"), employeeId));

            Query query = session.createQuery(criteriaQuery);
            List<Employee> results = query.getResultList();

            if (results.size() != 1)
                return null;
            else {
                results.get(0).setExpenses(null);
                return results.get(0);
            }

        }
    }
}
