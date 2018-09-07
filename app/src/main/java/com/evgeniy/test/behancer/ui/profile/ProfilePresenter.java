package com.evgeniy.test.behancer.ui.profile;

import com.arellomobile.mvp.InjectViewState;
import com.evgeniy.test.behancer.common.BasePresenter;
import com.evgeniy.test.behancer.data.Storage;
import com.evgeniy.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    private Storage mStorage;


    public ProfilePresenter(Storage storage) {
        mStorage = storage;
    }

    public void getProfile(String username){
        mCompositeDisposable.add(ApiUtils.getApiService().getUserInfo(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(username) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        response -> getViewState().showProfile(response.getUser()),
                        throwable -> getViewState().showError()));
    }
}
