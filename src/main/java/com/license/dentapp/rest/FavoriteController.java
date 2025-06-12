package com.license.dentapp.rest;

import com.license.dentapp.entity.Favorite;
import com.license.dentapp.entity.User;
import com.license.dentapp.service.FavoriteService;
import com.license.dentapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService,UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }


    @PostMapping("/{clientId}/{dentistId}")
    public ResponseEntity<Favorite> addFavorite(
            @PathVariable Integer clientId,
            @PathVariable Integer dentistId
    ) {
        Favorite created = favoriteService.addFavorite(clientId, dentistId);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{clientId}/{dentistId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Integer clientId,
            @PathVariable Integer dentistId
    ) {
        favoriteService.removeFavorite(clientId, dentistId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{clientId}")
    public ResponseEntity<List<User>> getFavorites(
            @PathVariable("clientId") Integer clientId
    ) {
        List<Favorite> favorites = favoriteService.getFavoritesForClient(clientId);
        List<User> dentists = favorites.stream()
                .map(Favorite::getDentist)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dentists);
    }
}
