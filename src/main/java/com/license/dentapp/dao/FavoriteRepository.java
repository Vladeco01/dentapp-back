package com.license.dentapp.dao;

import com.license.dentapp.entity.Favorite;
import com.license.dentapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Optional<Favorite> findByClientAndDentist(User client, User dentist);

    void deleteByClientAndDentist(User client, User dentist);

    List<Favorite> findAllByClient(User client);
}
