package com.evgeniy.test.behancer.ui.projects;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evgeniy.test.behancer.R;
import com.evgeniy.test.behancer.data.model.project.Project;
import com.evgeniy.test.behancer.databinding.ProjectBinding;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsHolder> {

    @NonNull
    private final List<Project> mProjects = new ArrayList<>();
    private final OnItemClickListener mOnItemClickListener;

    public ProjectsAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProjectBinding binding = ProjectBinding.inflate(inflater, parent, false);

        return new ProjectsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsHolder holder, int position) {
        Project project = mProjects.get(position);
        holder.bind(project, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public void addData(List<Project> data, boolean isRefreshed) {
        if (isRefreshed) {
            mProjects.clear();
        }

        // TODO: 09.04.2018 ДЗ обработать кейс с data.size == 0 || data == null

        mProjects.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        void onItemClick(String username);
    }
}
