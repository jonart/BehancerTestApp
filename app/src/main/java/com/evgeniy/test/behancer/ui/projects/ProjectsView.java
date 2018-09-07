package com.evgeniy.test.behancer.ui.projects;

import android.support.annotation.NonNull;

import com.evgeniy.test.behancer.common.BaseView;
import com.evgeniy.test.behancer.data.model.project.Project;

import java.util.List;

public interface ProjectsView extends BaseView {
    void showProjects(@NonNull List<Project> projects);

    void openProfileFragment(@NonNull String username);
}
