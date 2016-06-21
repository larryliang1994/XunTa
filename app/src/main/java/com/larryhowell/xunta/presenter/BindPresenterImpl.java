package com.larryhowell.xunta.presenter;

import android.os.Handler;

public class BindPresenterImpl implements IBindPresenter {
    private IBindView iBindView;

    public BindPresenterImpl(IBindView iBindView) {
        this.iBindView = iBindView;
    }

    @Override
    public void bind(String telephone) {
        new Handler().postDelayed(() -> {
            //iBindView.onBindResult(false, "不存在该用户");
            iBindView.onBindResult(true, "");
        }, 2000);
    }
}
