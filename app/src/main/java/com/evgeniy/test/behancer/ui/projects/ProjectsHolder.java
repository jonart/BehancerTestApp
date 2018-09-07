package com.evgeniy.test.behancer.ui.projects;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.evgeniy.test.behancer.R;
import com.evgeniy.test.behancer.data.model.project.Project;
import com.evgeniy.test.behancer.databinding.ProjectBinding;
import com.evgeniy.test.behancer.utils.DateUtils;
import com.squareup.picasso.Picasso;

public class ProjectsHolder extends RecyclerView.ViewHolder {

    private ProjectBinding mProjectBinding;

    public ProjectsHolder(ProjectBinding binding) {
        super(binding.getRoot());
        mProjectBinding = binding;
    }

    public void bind(Project item, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        mProjectBinding.setProject(new ProjectListItemViewModel(item));
        mProjectBinding.setOnItemClickListener(onItemClickListener);
        mProjectBinding.executePendingBindings();
    }
}
