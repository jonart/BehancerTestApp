package com.evgeniy.test.behancer.ui.projects;

import android.support.v4.app.Fragment;

import com.evgeniy.test.behancer.AppDelegate;
import com.evgeniy.test.behancer.common.SingleFragmentActivity;
import com.evgeniy.test.behancer.data.Storage;

public class ProjectsActivity extends SingleFragmentActivity implements Storage.StorageOwner {

    @Override
    protected Fragment getFragment() {
        return ProjectsFragment.newInstance();
    }

    @Override
    public Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }
}
