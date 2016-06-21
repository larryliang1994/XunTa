package com.larryhowell.xunta.presenter;

import android.content.SharedPreferences;
import android.os.Handler;

import com.larryhowell.xunta.App;
import com.larryhowell.xunta.common.Config;
import com.larryhowell.xunta.common.Constants;

public class LoginPresenterImpl implements ILoginPresenter {
    private ILoginView iLoginView;

    public LoginPresenterImpl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void login(String telephone) {
        new Handler().postDelayed(() -> {
            Config.nickname = "Leung Howell";
            Config.portrait = "http://taskmoment.image.alimmdn.com/portrait/12465.jpg";
            Config.telephone = telephone;

            SharedPreferences.Editor editor = App.sp.edit();
            editor.putString(Constants.SP_KEY_TELEPHONE, Config.telephone);
            editor.putString(Constants.SP_KEY_NICKNAME, Config.nickname);
            editor.putString(Constants.SP_KEY_PORTRAIT, Config.portrait);
            editor.apply();

            iLoginView.onLoginResult(true, "");
        }, 2000);
    }
}
