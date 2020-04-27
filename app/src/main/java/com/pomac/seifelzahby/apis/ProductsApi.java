package com.pomac.seifelzahby.apis;

import com.pomac.seifelzahby.model.responses.ProductsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductsApi {

    @GET("products")
    Observable<ProductsResponse> getProducts(@Query("category_id") int categoryId);


}
