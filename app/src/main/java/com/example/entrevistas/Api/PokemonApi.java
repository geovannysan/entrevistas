package com.example.entrevistas.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PokemonApi {
    @GET
    Call<String> getpokemon(@Url String url);
}
