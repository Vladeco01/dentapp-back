package com.license.dentapp.rest;

import com.license.dentapp.dto.ClinicRequest;
import com.license.dentapp.entity.Clinic;
import com.license.dentapp.entity.User;
import com.license.dentapp.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    @Autowired
    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping("/createClinic")
    public ResponseEntity<?> createClinic(@RequestBody ClinicRequest clinic, @AuthenticationPrincipal User user){
        clinicService.createClinic(clinic,user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteClinic/{clinicId}")
    public void deleteClinic(Integer clinicId){
        clinicService.deleteClinic(clinicId);
    }

    @PutMapping("/{clinicId}/add-dentist/{dentistId}")
    public ResponseEntity<?> addDentistToClinic(@PathVariable int clinicId, @PathVariable int dentistId) {
        clinicService.addDentistToClinic(dentistId, clinicId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clinicId}/get-dentists")
    public ResponseEntity<List<User>> findDentistsOfClinic(@PathVariable int clinicId){
        List<User> dentists = clinicService.getDentistsOfClinic(clinicId);
        return ResponseEntity.ok(dentists);
    }

    @GetMapping("/get-clinics")
    public ResponseEntity<List<Clinic>> getAllClinics(){
        List<Clinic> clinics = clinicService.getAllClinics();
        return ResponseEntity.ok(clinics);
    }

    @GetMapping("/get-clinic/{dentistId}")
    public ResponseEntity<String> getClinicForDentist(@PathVariable int dentistId){
        Clinic clinic = clinicService.getClinicOfDentist(dentistId);
        String result = "Clinica: " + clinic.getName() + " Adresa: " + clinic.getAddress();
        return ResponseEntity.ok(result);
    }
}
