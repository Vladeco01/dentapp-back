package com.license.dentapp.service;

import com.license.dentapp.entity.Favorite;
import com.license.dentapp.entity.User;
import com.license.dentapp.dao.FavoriteRepository;
import com.license.dentapp.dao.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Favorite addFavorite(Integer clientId, Integer dentistId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Clientul cu ID " + clientId + " nu a fost găsit"));
        User dentist = userRepository.findById(dentistId)
                .orElseThrow(() -> new RuntimeException("Dentistul cu ID " + dentistId + " nu a fost găsit"));

        Optional<Favorite> exista = favoriteRepository.findByClientAndDentist(client, dentist);
        if (exista.isPresent()) {
            throw new RuntimeException("Acest dentist este deja in lista de favorite ale clientului.");
        }

        Favorite fav = new Favorite();
        fav.setClient(client);
        fav.setDentist(dentist);
        return favoriteRepository.save(fav);
    }

    @Transactional
    public void removeFavorite(Integer clientId, Integer dentistId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Clientul cu ID " + clientId + " nu a fost găsit"));
        User dentist = userRepository.findById(dentistId)
                .orElseThrow(() -> new RuntimeException("Dentistul cu ID " + dentistId + " nu a fost găsit"));

        favoriteRepository.deleteByClientAndDentist(client, dentist);
    }

    @Transactional(readOnly = true)
    public List<Favorite> getFavoritesForClient(Integer clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Clientul cu ID " + clientId + " nu a fost găsit"));
        return favoriteRepository.findAllByClient(client);
    }
}
