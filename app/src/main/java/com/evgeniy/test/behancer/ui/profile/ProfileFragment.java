package com.evgeniy.test.behancer.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.evgeniy.test.behancer.R;
import com.evgeniy.test.behancer.common.PresenterFragment;
import com.evgeniy.test.behancer.common.RefreshOwner;
import com.evgeniy.test.behancer.common.Refreshable;
import com.evgeniy.test.behancer.data.Storage;
import com.evgeniy.test.behancer.data.model.user.User;
import com.evgeniy.test.behancer.ui.userprojects.UserProjectsFragment;
import com.evgeniy.test.behancer.utils.DateUtils;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends PresenterFragment
        implements ProfileView,Refreshable {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mProfileView;
    private String mUsername;
    private Storage mStorage;

    @InjectPresenter
    ProfilePresenter mPresenter;

    @ProvidePresenter
    ProfilePresenter providePresenter(){
        return new ProfilePresenter(mStorage);
    }

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;
    private Button mButton;

    @Override
    protected ProfilePresenter getPresenter() {
        return mPresenter;
    }


    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStorage = context instanceof Storage.StorageOwner ? ((Storage.StorageOwner) context).obtainStorage() : null;
        mRefreshOwner = context instanceof RefreshOwner ? (RefreshOwner) context : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mProfileView = view.findViewById(R.id.view_profile);

        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);
        mButton = view.findViewById(R.id.btn_project);
        mButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new UserProjectsFragment().newInstance(mUsername))
                    .addToBackStack(String.valueOf(UserProjectsFragment.class))
                    .commit();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mUsername = getArguments().getString(PROFILE_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }

        mProfileView.setVisibility(View.VISIBLE);

        onRefreshData();
    }


    @Override
    public void showProfile(User user) {
        mErrorView.setVisibility(View.GONE);
        mProfileView.setVisibility(View.VISIBLE);
        Picasso.with(getContext())
                .load(user.getImage().getPhotoUrl())
                .fit()
                .into(mProfileImage);
        mProfileName.setText(user.getDisplayName());
        mProfileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.setText(user.getLocation());
    }

    @Override
    public void onRefreshData() {
        getProfile();
    }

    private void getProfile() {
        mPresenter.getProfile(mUsername);
    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        mPresenter.disposeAll();
        super.onDetach();
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
        mProfileView.setVisibility(View.GONE);
    }
}
