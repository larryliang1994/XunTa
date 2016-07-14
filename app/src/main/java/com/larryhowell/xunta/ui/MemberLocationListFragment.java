package com.larryhowell.xunta.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MemberLocationListFragment extends Fragment implements ILocationPresenter.ILocationView {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.ll_empty)
    LinearLayout mEmptyLinearLayout;

    private LocationListAdapter mAdapter;
    public boolean loaded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_location_list, container, false);

        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
        mSwipeRefreshLayout.setEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
    }

    public void refresh() {
        if (!Config.isConnected) {
            UtilBox.showSnackbar(getActivity(), R.string.cant_access_network);

            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        mSwipeRefreshLayout.setRefreshing(true);

        new LocationPresenterImpl(this).getLocationList(((MemberMainActivity) getActivity()).mPerson.getTelephone());
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
                    layoutParams.height = locationList.size() * (UtilBox.dip2px(getActivity(), 64) + 1);
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
            UtilBox.showSnackbar(getActivity(), info);
        }
    }

    @Override
    public void onGetLocationResult(Boolean result, PoiInfo location) {
    }
}
