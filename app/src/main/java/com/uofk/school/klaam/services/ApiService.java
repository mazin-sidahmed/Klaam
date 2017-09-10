package com.uofk.school.klaam.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private SignLanguageApi signLanguageApi;

    public ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://signlanguage-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.signLanguageApi = retrofit.create(SignLanguageApi.class);
    }

    public SignLanguageApi getSignLanguageApi() {
        return signLanguageApi;
    }
}
