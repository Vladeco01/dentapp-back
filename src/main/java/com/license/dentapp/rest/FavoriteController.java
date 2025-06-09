package com.license.dentapp.rest;

import com.license.dentapp.entity.Favorite;
import com.license.dentapp.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
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
    public ResponseEntity<List<Favorite>> getFavorites(
            @PathVariable("clientId") Integer clientId
    ) {
        List<Favorite> favorites = favoriteService.getFavoritesForClient(clientId);
        return ResponseEntity.ok(favorites);
    }
}
