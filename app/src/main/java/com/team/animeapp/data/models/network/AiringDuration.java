package com.team.animeapp.data.models.network;

import com.squareup.moshi.Json;

import java.time.LocalDateTime;

public class AiringDuration {

    public LocalDateTime from;

    public LocalDateTime to;

    @Json(name = "string")
    String stringFormat;

}
