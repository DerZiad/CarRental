package com.coding.app.repository;

import com.coding.app.models.Reservation;
import com.coding.app.models.key.KeyReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, KeyReservation> {

}
