package com.evgeniy.test.behancer.ui.profile;

import com.evgeniy.test.behancer.common.BaseView;
import com.evgeniy.test.behancer.data.model.user.User;

public interface ProfileView extends BaseView{

    void showProfile(User user);

}
