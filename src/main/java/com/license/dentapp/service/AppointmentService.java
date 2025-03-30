package com.license.dentapp.service;

import com.license.dentapp.entity.Appointment;
import com.license.dentapp.entity.User;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();
    Appointment findById(Integer theId);
    Appointment findByUser(User theUser);
    Appointment save(Appointment theAppointment);
    void deleteById(int theId);
}
