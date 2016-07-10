package com.larryhowell.xunta.presenter;

public interface IGetLocationPresenter {
    void getLocation(String telephone);

    interface IGetLocationView {
        void onGetLocationResult(Boolean result, String info);
    }
}
