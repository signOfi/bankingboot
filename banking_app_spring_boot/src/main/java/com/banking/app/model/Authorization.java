package com.banking.app.model;

import com.banking.app.util.EmployeeAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private User employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @Column(nullable = false)
    private Date timestamp;

    @Column(nullable = false)
    private EmployeeAction employeeAction;

    public Authorization(User employee, Account account, Date timestamp, EmployeeAction employeeAction) {
        this.employee = employee;
        this.account = account;
        this.timestamp = timestamp;
        this.employeeAction = employeeAction;
    }
}
