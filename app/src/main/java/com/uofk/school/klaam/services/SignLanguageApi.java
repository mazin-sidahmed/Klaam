package com.uofk.school.klaam.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SignLanguageApi {

    @GET("/instructions/search")
    Call<List<String>> search(@Query("q") String query);
}
