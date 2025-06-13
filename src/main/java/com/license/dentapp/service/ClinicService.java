package com.license.dentapp.service;

import com.license.dentapp.dao.ClinicRepository;
import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.dto.ClinicRequest;
import com.license.dentapp.entity.Clinic;
import com.license.dentapp.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClinicService {

    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;

    public ClinicService(UserRepository userRepository, ClinicRepository clinicRepository) {
        this.userRepository = userRepository;
        this.clinicRepository = clinicRepository;
    }

    public void createClinic(ClinicRequest req, User client){
        Clinic clinic = new Clinic();
        List<User> users = new ArrayList<>();
        users.add(client);
        clinic.setDentists(users);
        clinic.setAddress(req.getAddress());
        clinic.setName(req.getName());
        clinicRepository.save(clinic);

    }

    public void deleteClinic(Integer clinicId){
        clinicRepository.deleteById(clinicId);
    }

    public void addDentistToClinic(int dentistId, int clinicId){
        Optional<User> optUser = userRepository.findById(dentistId);
        Optional<Clinic> optionalClinic = clinicRepository.findById(clinicId);

        if(optUser.isPresent() && optionalClinic.isPresent()){
            User user = optUser.get();
            Clinic clinic = optionalClinic.get();
            user.setClinic(clinic);
            userRepository.save(user);
            clinic.getDentists().add(user);
        }
        else {
            System.out.println("EROARE, NU SE GASESTE USER");
        }
    }

    public List<User> getDentistsOfClinic(int clinicId){
        return userRepository.findByClinicId(clinicId);
    }

    public List<Clinic> getAllClinics(){
        return clinicRepository.findAll();
    }

    public Clinic getClinicOfDentist(int dentistId) { return clinicRepository.findByDentistId(dentistId);}
}
