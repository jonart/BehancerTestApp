package com.evgeniy.test.behancer.ui.projects;

import com.arellomobile.mvp.InjectViewState;
import com.evgeniy.test.behancer.BuildConfig;
import com.evgeniy.test.behancer.common.BasePresenter;
import com.evgeniy.test.behancer.data.Storage;
import com.evgeniy.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {
    private Storage mStorage;

    public ProjectsPresenter(Storage storage) {
        mStorage = storage;
    }

    public void getProjects() {
        mCompositeDisposable.add(ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        response -> getViewState().showProjects(response.getProjects()),
                        throwable -> getViewState().showError()));
    }

    public void openProfileFragment(String username) {
        getViewState().openProfileFragment(username);
    }
}
