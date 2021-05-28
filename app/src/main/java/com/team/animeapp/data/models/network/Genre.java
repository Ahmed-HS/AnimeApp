package com.team.animeapp.data.models.network;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Genre {

    public static List<Genre> allGenres;

    static {
        allGenres = new ArrayList<>();
        allGenres.add(new Genre(1,"Action"));
        allGenres.add(new Genre(2,"Adventure"));
        allGenres.add(new Genre(4,"Comedy"));
        allGenres.add(new Genre(8,"Drama"));
        allGenres.add(new Genre(10,"Fantasy"));
        allGenres.add(new Genre(27,"Shounen"));
        allGenres.add(new Genre(23,"School"));
        allGenres.add(new Genre(15,"Kids"));
        allGenres.add(new Genre(11,"Game"));
        allGenres.add(new Genre(24,"Sci-Fi"));
        allGenres.add(new Genre(37,"Supernatural"));
        allGenres.add(new Genre(36,"Slice Of Life"));
    }

    public Genre(int malId, String name) {
        this.malId = malId;
        this.name = name;
    }

    @Json(name = "mal_id")
    public int malId;

    public String type;
    public String name;
    public String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return malId == genre.malId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(malId);
    }
}
