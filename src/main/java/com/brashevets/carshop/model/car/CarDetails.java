package com.brashevets.carshop.model.car;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A CarDetails.
 */
@Entity
@Table(name = "car_details")
public class CarDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull        
    @Column(nullable = false)
    private String value;

    @ManyToOne
    private DefaultCarMeta defaultCarMeta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DefaultCarMeta getDefaultCarMeta() {
        return defaultCarMeta;
    }

    public void setDefaultCarMeta(DefaultCarMeta defaultCarMeta) {
        this.defaultCarMeta = defaultCarMeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarDetails carDetails = (CarDetails) o;

        if ( ! Objects.equals(id, carDetails.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarDetails{" +
                "id=" + id +
                ", value='" + value + "'" +
                '}';
    }
}
