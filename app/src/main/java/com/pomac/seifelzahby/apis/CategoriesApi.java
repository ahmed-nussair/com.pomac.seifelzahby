package com.pomac.seifelzahby.apis;

import com.pomac.seifelzahby.model.responses.CategoriesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CategoriesApi {

    @GET("categories")
    Observable<CategoriesResponse> getCategories();
}
