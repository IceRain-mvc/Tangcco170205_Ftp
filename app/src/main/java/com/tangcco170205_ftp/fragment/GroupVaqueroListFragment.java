package com.tangcco170205_ftp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.adapter.GroupVaqueroAdapter;
import com.tangcco170205_ftp.listener.OnRecyclerItemClickListener;
import com.tangcco170205_ftp.utils.FloatUtils;
import com.tangcco170205_ftp.weight.DividerItemDecoration;

import butterknife.ButterKnife;


/**
 * Created by katsuyagoto on 15/06/19.
 */
public class GroupVaqueroListFragment extends Fragment implements OnRecyclerItemClickListener {


    private RecyclerView mRecyclerView;
    private GroupVaqueroAdapter mGroupListAdapter;

    public GroupVaqueroListFragment() {
    }

    public static GroupVaqueroListFragment newInstance() {
        return new GroupVaqueroListFragment();
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
        mRecyclerView = ButterKnife.findById(view, R.id.group_caquero_recycler_view);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(FloatUtils.getDrawableResource(getActivity(), R.drawable.line)));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mGroupListAdapter);
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
