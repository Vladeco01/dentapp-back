package com.license.dentapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic; // For dentists only

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> clientAppointments;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> dentistAppointments;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;

    // Getters and setters


    public User() {
    }

    public User(Integer id, String name, String password, String email, Role role, Clinic clinic, List<Appointment> clientAppointments, List<Appointment> dentistAppointments, List<Favorite> favorites) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.clinic = clinic;
        this.clientAppointments = clientAppointments;
        this.dentistAppointments = dentistAppointments;
        this.favorites = favorites;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public List<Appointment> getClientAppointments() {
        return clientAppointments;
    }

    public void setClientAppointments(List<Appointment> clientAppointments) {
        this.clientAppointments = clientAppointments;
    }

    public List<Appointment> getDentistAppointments() {
        return dentistAppointments;
    }

    public void setDentistAppointments(List<Appointment> dentistAppointments) {
        this.dentistAppointments = dentistAppointments;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", clinic=" + clinic +
                ", clientAppointments=" + clientAppointments +
                ", dentistAppointments=" + dentistAppointments +
                ", favorites=" + favorites +
                '}';
    }
}
