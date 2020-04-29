package com.pomac.seifelzahby.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.seifelzahby.Globals;
import com.pomac.seifelzahby.apis.CartApi;
import com.pomac.seifelzahby.model.responses.CheckoutResponse;

import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutViewModel extends ViewModel {

    private MutableLiveData<CheckoutResponse> checkoutResponseMutableLiveData;
    private Disposable disposable;

    public LiveData<CheckoutResponse> getCheckoutResponse(String sessionCode, Map<String, String> checkoutData) {
        if (checkoutResponseMutableLiveData == null) {
            checkoutResponseMutableLiveData = new MutableLiveData<>();
            checkout(sessionCode, checkoutData);
        }
        return checkoutResponseMutableLiveData;
    }

    private void checkout(String sessionCode, Map<String, String> checkoutData) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CartApi api = retrofit.create(CartApi.class);

        Observable<CheckoutResponse> observable = api.checkout(sessionCode,
                checkoutData.get(Globals.CHECKOUT_ADDRESS),
                checkoutData.get(Globals.CHECKOUT_NAME),
                checkoutData.get(Globals.CHECKOUT_MOBILE),
                checkoutData.get(Globals.CHECKOUT_NOTES))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        disposable = observable.subscribe(response -> checkoutResponseMutableLiveData.setValue(response),
                e -> Log.e("nussair", Objects.requireNonNull(e.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
