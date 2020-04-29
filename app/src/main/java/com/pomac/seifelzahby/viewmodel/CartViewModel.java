package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.CartApi;
import com.pomac.seifelzahby.model.responses.CartResponse;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartViewModel extends ViewModel {

    private MutableLiveData<CartResponse> cartResponse;
    private Disposable disposable;
//    private Observer<CartResponse> observer;

    public LiveData<CartResponse> getCartResponse(String sessionCode) {
        if (cartResponse == null) {
            cartResponse = new MutableLiveData<>();
            loadingCart(sessionCode);
        }
        return cartResponse;
    }

    private void loadingCart(String sessionCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CartApi api = retrofit.create(CartApi.class);

//        Call<CartResponse> cartResponseCall = api.getCart(sessionCode);
//
//        cartResponseCall.enqueue(new Callback<CartResponse>() {
//            @Override
//            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
//                cartResponse.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<CartResponse> call, Throwable t) {
//                Log.e("nussair", Objects.requireNonNull(t.getMessage()));
//            }
//        });



        Observable<CartResponse> observable = api.getCart(sessionCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = observable.subscribe(response -> cartResponse.setValue(response),
                e -> Log.e("nussair", Objects.requireNonNull(e.getMessage())));


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
