package com.pomac.seifelzahby.apis;

import com.pomac.seifelzahby.model.responses.AddingToCartResponse;
import com.pomac.seifelzahby.model.responses.CartResponse;
import com.pomac.seifelzahby.model.responses.CheckoutResponse;
import com.pomac.seifelzahby.model.responses.DeletingFromCartResponse;
import com.pomac.seifelzahby.model.responses.UpdatingCartResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartApi {

    @GET("cart")
    Observable<CartResponse> getCart(@Query("session_code") String sessionCode);

    @POST("cart/add")
    Observable<AddingToCartResponse> addToCart(@Field("product_id") int productId,
                                               @Field("quantity") int quantity,
                                               @Field("session_code") String sessionCode);

    @POST("cart/update")
    Observable<UpdatingCartResponse> updateCart(@Field("cart_id") int cartId,
                                                @Field("quantity") int quantity,
                                                @Field("session_code") String sessionCode);

    @POST("cart/delete")
    Observable<DeletingFromCartResponse> deleteFromCart(@Field("cart_id") int cartId,
                                                        @Field("session_code") String sessionCode);

    Observable<CheckoutResponse> checkout(@Field("session_code") String sessionCode,
                                          @Field("address") String address,
                                          @Field("name") String name,
                                          @Field("phone") String phone,
                                          @Field("notes") String notes);
}
