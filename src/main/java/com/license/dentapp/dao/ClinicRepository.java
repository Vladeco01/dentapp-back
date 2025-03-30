package com.license.dentapp.dao;

import com.license.dentapp.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic,Integer> {
}
