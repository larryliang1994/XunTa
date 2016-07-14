package com.larryhowell.xunta.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.baidu.mapapi.search.core.PoiInfo;
import com.larryhowell.xunta.R;
import com.larryhowell.xunta.adapter.LocationListAdapter;
import com.larryhowell.xunta.bean.Location;
import com.larryhowell.xunta.common.Config;
import com.larryhowell.xunta.common.UtilBox;
import com.larryhowell.xunta.presenter.ILocationPresenter;
import com.larryhowell.xunta.presenter.LocationPresenterImpl;
import com.larryhowell.xunta.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationListActivity extends BaseActivity implements ILocationPresenter.ILocationView {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.ll_empty)
    LinearLayout mEmptyLinearLayout;

    private LocationListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
        mSwipeRefreshLayout.setEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));

        // 延迟执行才能使旋转进度条显示出来
        new Handler().postDelayed(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            refresh();
        }, 200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        if (!Config.isConnected) {
            UtilBox.showSnackbar(this, R.string.cant_access_network);

            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        mSwipeRefreshLayout.setRefreshing(true);

        new LocationPresenterImpl(this).getLocationList(Config.telephone);
    }

    @Override
    public void onGetLocationListResult(Boolean result, String info, List<Location> locationList) {
        mSwipeRefreshLayout.setRefreshing(false);

        if (result) {
            if (locationList.size() == 0) {
                mEmptyLinearLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mEmptyLinearLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                if (mAdapter == null) {
                    mAdapter = new LocationListAdapter();
                    mAdapter.setLocationList(locationList);

                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mRecyclerView.getLayoutParams();
                    layoutParams.height = locationList.size() * (UtilBox.dip2px(this, 64) + 1);
                    mRecyclerView.setLayoutParams(layoutParams);

                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setLocationList(locationList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        } else {
            mEmptyLinearLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            UtilBox.showSnackbar(this, info);
        }
    }

    @Override
    public void onGetLocationResult(Boolean result, PoiInfo location) {
    }
}
