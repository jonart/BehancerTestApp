package com.evgeniy.test.behancer.ui.profile;

import android.support.v4.app.Fragment;

import com.evgeniy.test.behancer.AppDelegate;
import com.evgeniy.test.behancer.common.RefreshActivity;
import com.evgeniy.test.behancer.common.SingleFragmentActivity;
import com.evgeniy.test.behancer.data.Storage;

public class ProfileActivity extends RefreshActivity implements Storage.StorageOwner {

    public static final String USERNAME_KEY = "USERNAME_KEY";

    @Override
    protected Fragment getFragment() {
        if (getIntent() != null) {
            return ProfileFragment.newInstance(getIntent().getBundleExtra(USERNAME_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }

    @Override
    public Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }
}

