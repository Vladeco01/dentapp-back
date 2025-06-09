package com.license.dentapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "clinic")
    @JsonIgnore
    private List<User> dentists;

    // Getters and setters

    public Clinic() {
    }

    public Clinic(Long id, String name, String address, List<User> dentists) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dentists = dentists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getDentists() {
        return dentists;
    }

    public void setDentists(List<User> dentists) {
        this.dentists = dentists;
    }

    @Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dentists=" + dentists +
                '}';
    }
}
