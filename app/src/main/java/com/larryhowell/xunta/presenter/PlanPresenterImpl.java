package com.larryhowell.xunta.presenter;

import android.content.SharedPreferences;
import android.os.Handler;

import com.larryhowell.xunta.App;
import com.larryhowell.xunta.bean.Plan;
import com.larryhowell.xunta.common.Config;
import com.larryhowell.xunta.common.Constants;

public class PlanPresenterImpl implements IPlanPresenter {
    private IPlanView iPlanView;

    public PlanPresenterImpl(IPlanView iPlanView) {
        this.iPlanView = iPlanView;
    }

    @Override
    public void makePlan(Plan plan) {
        new Handler().postDelayed(() -> {
            iPlanView.onMakePlanResult(true, "");
        }, 2000);
    }

    @Override
    public void getPlan() {
        new Handler().postDelayed(() -> {
            Config.plan = null;
            iPlanView.onGetPlanResult(true, "");
        }, 2000);
    }
}
