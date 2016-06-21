package com.larryhowell.xunta.presenter;

import android.os.Handler;

import com.larryhowell.xunta.common.Config;

/**
 * Created by Leunghowell on 16/6/12.
 */
public class GetUserInfoPresenterImpl implements IGetUserInfoPresenter {
    public IGetUserInfoView iGetUserInfoView;

    public GetUserInfoPresenterImpl(IGetUserInfoView iGetUserInfoView) {
        this.iGetUserInfoView = iGetUserInfoView;
    }

    @Override
    public void getUserInfo(String telephone) {
        if (telephone == null || "".equals(telephone)) {
            iGetUserInfoView.onGetUserInfoResult(true, "");
            return;
        }

        new Handler().postDelayed(() -> {
            Config.nickname = "Leung Howell";
            Config.portrait = "http://taskmoment.image.alimmdn.com/portrait/12465.jpg";

            iGetUserInfoView.onGetUserInfoResult(true, "");
        }, 2000);
    }
}


