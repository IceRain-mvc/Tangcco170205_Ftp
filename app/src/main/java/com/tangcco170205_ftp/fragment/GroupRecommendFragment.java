package com.tangcco170205_ftp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.listener.OnRecyclerItemClickListener;

import butterknife.ButterKnife;


/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupRecommendFragment extends Fragment implements OnRecyclerItemClickListener {


    public GroupRecommendFragment() {
    }

    public static GroupRecommendFragment newInstance() {
        return new GroupRecommendFragment();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_vaquero, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClickRecyclerItem(View v, int position) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
