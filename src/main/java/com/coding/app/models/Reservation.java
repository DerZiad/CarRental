package com.coding.app.models;

import com.coding.app.models.key.KeyReservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation implements Serializable {

    @EmbeddedId
    private KeyReservation id;

    @Column
    boolean isConfirmed = false;

    private long dateCreation = System.currentTimeMillis();

    private Date startDay;

    private Date endDay;

    /**
     * Relations
     *
     */

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @MapsId("username") // maps the username field in KeyReservation
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @MapsId("carId") // maps the carId field in KeyReservation
    @JoinColumn(name = "carId", referencedColumnName = "id")
    @JsonIgnore
    private Car car;

    public boolean completed() {
        return new Date(System.currentTimeMillis()).after(this.endDay);
    }

    public boolean isOngoing() {
        final Date today = new Date(System.currentTimeMillis());
        return (today.equals(this.startDay) || today.after(this.startDay)) && (today.equals(this.endDay) || today.before(this.endDay));
    }
}
