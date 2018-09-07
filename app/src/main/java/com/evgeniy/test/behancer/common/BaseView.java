package com.evgeniy.test.behancer.common;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView{

    void showRefresh();

    void hideRefresh();

    void showError();

}
