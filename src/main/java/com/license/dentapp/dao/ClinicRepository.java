package com.license.dentapp.dao;

import com.license.dentapp.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic,Integer> {

    @Query("SELECT c FROM Clinic c JOIN c.dentists d WHERE d.id = :dentistId")
    Clinic findByDentistId(@Param("dentistId") Integer dentistId);
}
