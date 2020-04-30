package com.pomac.seifelzahby.apis;

import com.pomac.seifelzahby.model.responses.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {

    @GET("search")
    Observable<SearchResponse> getSearchResults(@Query("term") String term);
}
