package com.license.dentapp.dao;

import com.license.dentapp.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    @Query("""
        SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
        FROM Appointment a 
        WHERE a.dentist.id = :dentistId 
        AND (
            (:startTime BETWEEN a.startTime AND a.endTime) OR
            (:endTime BETWEEN a.startTime AND a.endTime) OR
            (a.startTime BETWEEN :startTime AND :endTime)
        )
    """)
    boolean existsByDentistIdAndTimeOverlap(@Param("dentistId") Integer dentistId,
                                            @Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime);

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.dentist.id = :dentistId
        ORDER BY a.startTime ASC
    """)
    List<Appointment> findAllByDentistId(@Param("dentistId") Integer dentistId);

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.client.id = :clientId
        ORDER BY a.startTime ASC
    """)
    List<Appointment> findAllByClientId(@Param("clientId") Integer clientId);
}
