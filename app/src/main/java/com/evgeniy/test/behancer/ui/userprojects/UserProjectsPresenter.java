package com.evgeniy.test.behancer.ui.userprojects;

import com.arellomobile.mvp.InjectViewState;
import com.evgeniy.test.behancer.common.BasePresenter;
import com.evgeniy.test.behancer.data.Storage;
import com.evgeniy.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
@InjectViewState
public class UserProjectsPresenter extends BasePresenter<UserProjectsView> {

     private Storage mStorage;

    public UserProjectsPresenter(Storage storage) {
        mStorage = storage;
    }

    public void getProjects(String username) {
        mCompositeDisposable.add(ApiUtils.getApiService().getUserProjects(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getProjectByName(username) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        response -> getViewState().showProjects(response.getProjects()),
                        throwable -> getViewState().showError()));
    }
}