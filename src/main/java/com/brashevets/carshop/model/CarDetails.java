package com.brashevets.carshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CarDetails.
 */
@Entity
@Table(name = "car_details")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CarDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull        
    @Column(name = "value", nullable = false)
    private String value;

   /* @ManyToMany(mappedBy = "carDetailss")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Car> cars = new HashSet<>();*/

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

   /* public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
*/
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
