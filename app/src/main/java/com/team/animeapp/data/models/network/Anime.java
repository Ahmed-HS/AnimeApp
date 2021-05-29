package com.team.animeapp.data.models.network;

import com.squareup.moshi.Json;

import java.time.LocalDateTime;
import java.util.List;

public class Anime {

    @Json(name = "mal_id")
    public int malId;

    @Json(name = "url")
    public String malUrl;

    @Json(name = "image_url")
    public String imageUrl;

    @Json(name = "trailer_url")
    public String trailerUrl;

    public String duration;

    @Json(name = "broadcast")
    public String broadcastTime;

    public String title;

    @Json(name = "airing")
    public boolean isAiring;

    public String synopsis;
    public String type;

//    @Json(name = "episodes")
//    public int episodesCount;

    public long rank;

    @Json(name = "aired")
    public AiringDuration airingDuration;

    @Json(name = "score")
    public float rating;

    @Json(name = "start_date")
    public LocalDateTime startDate;

    @Json(name = "end_date")
    public LocalDateTime endDate;

    public List<Studio> studios;
    public List<Genre> genres;
}
