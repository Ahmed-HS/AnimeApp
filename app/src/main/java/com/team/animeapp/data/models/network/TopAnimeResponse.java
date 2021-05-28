package com.team.animeapp.data.models.network;

import com.squareup.moshi.Json;

import java.util.List;

public class TopAnimeResponse {

    @Json(name = "top")
    public List<Anime> topAnimeList;
}
