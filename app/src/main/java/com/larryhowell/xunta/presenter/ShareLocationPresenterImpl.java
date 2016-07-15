package com.larryhowell.xunta.presenter;

import android.os.Handler;

public class ShareLocationPresenterImpl implements IShareLocationPresenter {
    private IShareLocationView iShareLocationView;

    public ShareLocationPresenterImpl(IShareLocationView iShareLocationView) {
        this.iShareLocationView = iShareLocationView;
    }

    @Override
    public void requestLocation(String target) {
        new Handler().postDelayed(() -> iShareLocationView.requestLocationResult(true, ""), 2000);
    }

    @Override
    public void confirmShare(boolean accept, String nickname, String sender) {
        new Handler().postDelayed(() -> iShareLocationView.confirmShareResult(true, ""), 2000);
    }
}
