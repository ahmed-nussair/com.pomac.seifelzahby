package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.CartApi;
import com.pomac.seifelzahby.model.responses.UpdatingCartResponse;

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

public class UpdatingCartViewModel extends ViewModel {

    private MutableLiveData<UpdatingCartResponse> updatingCartResponseMutableLiveData;
    private Disposable disposable;

    public LiveData<UpdatingCartResponse> getUpdatingCartResponse(int cartId, int quantity, String sessionCode) {
        if (updatingCartResponseMutableLiveData == null) {
            updatingCartResponseMutableLiveData = new MutableLiveData<>();
            updateCart(cartId, quantity, sessionCode);
        }
        return updatingCartResponseMutableLiveData;
    }

    private void updateCart(int cartId, int quantity, String sessionCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CartApi cartApi = retrofit.create(CartApi.class);

//        Call<UpdatingCartResponse> updatingCartResponseCall = cartApi.updateCart(cartId, quantity, sessionCode);
//
//        updatingCartResponseCall.enqueue(new Callback<UpdatingCartResponse>() {
//            @Override
//            public void onResponse(Call<UpdatingCartResponse> call, Response<UpdatingCartResponse> response) {
//                updatingCartResponseMutableLiveData.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<UpdatingCartResponse> call, Throwable t) {
//                Log.e("nussair", Objects.requireNonNull(t.getMessage()));
//            }
//        });

        Observable<UpdatingCartResponse> updatingCartResponseObservable = cartApi.updateCart(cartId, quantity, sessionCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = updatingCartResponseObservable.subscribe(response -> {
                    Log.d("nussair", "Response: " + response.getMessage());
                    updatingCartResponseMutableLiveData.setValue(response);
                },
                e -> Log.e("nussair", Objects.requireNonNull(e.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
