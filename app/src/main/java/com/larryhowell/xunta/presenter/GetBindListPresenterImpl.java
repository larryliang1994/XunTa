package com.larryhowell.xunta.presenter;

import android.os.Handler;

import com.larryhowell.xunta.bean.Person;
import com.larryhowell.xunta.common.Config;


public class GetBindListPresenterImpl implements IGetBindListPresenter {
    private IGetBindListView iGetBindListView;

    public GetBindListPresenterImpl(IGetBindListView iGetBindListView) {
        this.iGetBindListView = iGetBindListView;
    }

    @Override
    public void getBindList() {
        new Handler().postDelayed(() -> {
            Config.bindList.clear();

            Config.bindList.add(new Person("哇咔咔", "18168061837", "http://taskmoment.image.alimmdn.com/portrait/8786.jpg"));
            Config.bindList.add(new Person("炒鸡大傻逼", "18168061837", "http://taskmoment.image.alimmdn.com/portrait/17196.jpg"));
            Config.bindList.add(new Person("最熟悉的陌生银", "18168061837", "http://taskmoment.image.alimmdn.com/portrait/17197.jpg"));
            Config.bindList.add(new Person("呵呵", "18168061837", "http://taskmoment.image.alimmdn.com/portrait/12465.jpg"));

            iGetBindListView.OnGetBindListResult(true, "");
        }, 2000);
    }
}
