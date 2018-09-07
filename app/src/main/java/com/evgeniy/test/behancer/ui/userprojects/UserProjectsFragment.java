package com.evgeniy.test.behancer.ui.userprojects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.evgeniy.test.behancer.R;
import com.evgeniy.test.behancer.common.PresenterFragment;
import com.evgeniy.test.behancer.common.RefreshOwner;
import com.evgeniy.test.behancer.common.Refreshable;
import com.evgeniy.test.behancer.data.Storage;
import com.evgeniy.test.behancer.data.model.project.Project;

import java.util.List;

public class UserProjectsFragment extends PresenterFragment
        implements UserProjectsView, Refreshable, UserProjectsAdapter.OnItemClickListener {

    private static String mUsername;
    private RecyclerView mRecyclerView;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private Storage mStorage;
    private UserProjectsAdapter mProjectsAdapter;
    @InjectPresenter
    UserProjectsPresenter mPresenter;

    @ProvidePresenter
    UserProjectsPresenter providePresenter() {

        return new UserProjectsPresenter(mStorage);
    }

    @Override
    protected UserProjectsPresenter getPresenter() {
        return mPresenter;
    }

    public static UserProjectsFragment newInstance(String username) {

        Bundle args = new Bundle();

        mUsername = username;

        UserProjectsFragment fragment = new UserProjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }

        if (context instanceof RefreshOwner) {
            mRefreshOwner = ((RefreshOwner) context);
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects);
        }

        mProjectsAdapter = new UserProjectsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProjectsAdapter);

        onRefreshData();
    }

    @Override
    public void onItemClick(String username) {

    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showProjects(List<Project> projects) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProjectsAdapter.addData(projects, true);
    }

    @Override
    public void showRefresh() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshData() {
        mPresenter.getProjects(mUsername);
    }

}
