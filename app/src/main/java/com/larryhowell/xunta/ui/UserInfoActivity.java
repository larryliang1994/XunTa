package com.larryhowell.xunta.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.larryhowell.xunta.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInfoActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
