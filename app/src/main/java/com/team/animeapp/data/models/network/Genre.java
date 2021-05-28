package com.team.animeapp.data.models.network;

import com.squareup.moshi.Json;

public class Genre {

    @Json(name = "mal_id")
    public int malId;

    public String type;
    public String name;
    public String url;
}
