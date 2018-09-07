package com.evgeniy.test.behancer.ui.userprojects;

import android.support.annotation.NonNull;

import com.evgeniy.test.behancer.common.BaseView;
import com.evgeniy.test.behancer.data.model.project.Project;

import java.util.List;

public interface UserProjectsView extends BaseView {
    void showProjects(@NonNull List<Project> projects);
}
