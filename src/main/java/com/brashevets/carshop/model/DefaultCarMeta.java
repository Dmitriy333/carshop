package com.brashevets.carshop.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DefaultCarMeta.
 */
@Entity
@Table(name = "default_car_meta")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DefaultCarMeta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull        
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull        
    @Column(name = "value", nullable = false)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultCarMeta defaultCarMeta = (DefaultCarMeta) o;

        if ( ! Objects.equals(id, defaultCarMeta.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DefaultCarMeta{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", value='" + value + "'" +
                '}';
    }
}
