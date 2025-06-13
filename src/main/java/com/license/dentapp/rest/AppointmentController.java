package com.license.dentapp.rest;

import com.license.dentapp.dao.AppointmentRepository;
import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.dto.AppointmentRequest;
import com.license.dentapp.dto.AppointmentResponse;
import com.license.dentapp.entity.Appointment;
import com.license.dentapp.entity.User;
import com.license.dentapp.service.AppointmentService;
import com.license.dentapp.service.NotificationService;
import com.license.dentapp.utils.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final NotificationService notificationService;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentController(AppointmentService appointmentService, NotificationService notificationService,AppointmentRepository appointmentRepository,UserRepository userRepository) {
        this.appointmentService = appointmentService;
        this.notificationService = notificationService;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
        appointmentService.createAppointment(request);
        Optional<User> optionalUser = userRepository.findById(request.getClientId());
        User user = optionalUser.get();
        notificationService.createNotification(
                request.getDentistId(),
                "Ai primit o nouă cerere de programare de la userul " + user.getFirstName() + user.getLastName()
        );
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptAppointment(@PathVariable Integer id) {
        appointmentService.updateStatus(id, "ACCEPTED");
        Optional<Appointment> optAppointment = appointmentRepository.findById(id);
        Appointment appointment = optAppointment.get();
        notificationService.createNotification(
                appointment.getClient().getId(),
                "Dentistul " + appointment.getDentist().getFirstName() + " " + appointment.getDentist().getLastName() + " a acceptat programarea ta"
        );
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

    @GetMapping("/dentist/{dentistId}/pending")
    public List<AppointmentResponse> getPendingAppointmentsForDentist(@PathVariable Integer dentistId){
        return appointmentService.getPendingAppointmentsByDentist(dentistId);
    }
}
