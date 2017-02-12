package com.tangcco170205_ftp.fragment;

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
public class ListsFragment extends Fragment implements OnRecyclerItemClickListener {


    private static final String KEY_GROUP_NAME = "lists";


    public static ListsFragment newInstance() {
        return new ListsFragment();
    }

    public static ListsFragment newInstance(String groupName) {
        ListsFragment listsFragment = new ListsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_GROUP_NAME, groupName);
        listsFragment.setArguments(args);
        return listsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void refreshListByGroupName(String groupName) {
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
