package com.license.dentapp.rest;

import com.license.dentapp.dto.AppointmentRequest;
import com.license.dentapp.dto.AppointmentResponse;
import com.license.dentapp.entity.Appointment;
import com.license.dentapp.entity.User;
import com.license.dentapp.service.AppointmentService;
import com.license.dentapp.utils.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
        appointmentService.createAppointment(request);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptAppointment(@PathVariable Integer id) {
        appointmentService.updateStatus(id, "ACCEPTED");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/deny")
    public ResponseEntity<?> denyAppointment(@PathVariable Integer id) {
        appointmentService.updateStatus(id, "DENIED");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dentist/{dentistId}")
    public List<AppointmentResponse> getAppointmentsForDentist(@PathVariable Integer dentistId) {
        return appointmentService.getAppointmentsByDentist(dentistId);
    }

    @GetMapping("/client/{clientId}")
    public List<AppointmentResponse> getAppointmentsForClient(@PathVariable Integer clientId) {
        return appointmentService.getAppointmentsByUserId(clientId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Appointment> update(@RequestBody Appointment appointment) {
        Appointment updated = appointmentService.save(appointment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/free/{dentistId}")
    public List<String> getFreeSlots(@PathVariable Integer dentistId) {
        return appointmentService.getAvailableSlots(dentistId)
                .stream()
                .map(LocalDateTime::toString)
                .collect(Collectors.toList());
    }
}
