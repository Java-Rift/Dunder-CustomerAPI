package com.dunder.mifflin.customerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * Entity Class for Customer table.
 *
 * @author Edom Mesfin
 * @since 2022
 */
@Entity
@Table(name = "CUSTOMER")
@AllArgsConstructor
@NoArgsConstructor
public @Getter @Setter class Customer {

    @Id
    @Column(name = "UUID", nullable = false, unique = true)
    private String uuid;

    @Column(name = "FIRST_NAME", nullable = false)
    private String fName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lName;

    @Column(name = "CREDIT")
    private int credit;

    @Column(name = "CREATEDATE", updatable = false)
    private Date createdDate;

    @Column(name = "UPDATEDATE")
    private Date updatedDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;

        return Objects.equals(uuid, customer.uuid) && Objects.equals(this.fName, customer.fName) && Objects.equals(this.lName, customer.lName);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.uuid);
        hash = 97 * hash + Objects.hashCode(this.fName);
        hash = 97 * hash + Objects.hashCode(this.lName);
        return hash;
    }
}
