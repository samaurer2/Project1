package dev.maurer.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "MANAGER")
public class Manager extends Employee {

    @Column(name = "employee_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    public Manager() {
    }

    public Manager(int id) {
        super(id);
    }

    public Manager(String name, String password) {
        super(name, password);
    }

    @Override
    public EmployeeType getType() {
        return type;
    }

    @Override
    public void setType(EmployeeType type) {
        this.type = type;
    }


}
