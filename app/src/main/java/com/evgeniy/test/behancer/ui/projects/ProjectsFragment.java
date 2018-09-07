package com.evgeniy.test.behancer.ui.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.evgeniy.test.behancer.BuildConfig;
import com.evgeniy.test.behancer.R;
import com.evgeniy.test.behancer.common.PresenterFragment;
import com.evgeniy.test.behancer.data.model.project.Project;
import com.evgeniy.test.behancer.ui.profile.ProfileActivity;
import com.evgeniy.test.behancer.ui.profile.ProfileFragment;
import com.evgeniy.test.behancer.utils.ApiUtils;
import com.evgeniy.test.behancer.common.RefreshOwner;
import com.evgeniy.test.behancer.common.Refreshable;
import com.evgeniy.test.behancer.data.Storage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsFragment extends PresenterFragment
        implements ProjectsView, SwipeRefreshLayout.OnRefreshListener, ProjectsAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private View mErrorView;
    private Storage mStorage;
    private ProjectsAdapter mProjectsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectPresenter
    ProjectsPresenter mPresenter;

    @ProvidePresenter
    ProjectsPresenter providePresenter(){

        return new ProjectsPresenter(mStorage);
    }

    @Override
    protected ProjectsPresenter getPresenter() {
        return mPresenter;
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mErrorView = view.findViewById(R.id.errorView);
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects);
        }

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mProjectsAdapter = new ProjectsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProjectsAdapter);

        onRefresh();
    }

    @Override
    public void onItemClick(String username) {
        mPresenter.openProfileFragment(username);
    }

    @Override
    public void onDetach() {
        mStorage = null;
        super.onDetach();
    }

    @Override
    public void showProjects(List<Project> projects) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProjectsAdapter.addData(projects, true);
    }

    @Override
    public void openProfileFragment(String username) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        Bundle args = new Bundle();
        args.putString(ProfileFragment.PROFILE_KEY, username);
        intent.putExtra(ProfileActivity.USERNAME_KEY, args);
        startActivity(intent);
    }

    @Override
    public void showRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        mPresenter.getProjects();
    }
}
