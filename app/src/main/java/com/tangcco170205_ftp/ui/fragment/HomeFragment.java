package com.tangcco170205_ftp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.adapter.AllDataAdapter;
import com.tangcco170205_ftp.bean.AllDataBean;
import com.tangcco170205_ftp.bean.EventBusBean;
import com.tangcco170205_ftp.db.DBHelper;
import com.tangcco170205_ftp.listener.OnClickMainFABListener;
import com.tangcco170205_ftp.listener.OnMakeSnackbar;
import com.tangcco170205_ftp.listener.OnRecyclerItemClickListener;

import java.util.List;

import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;


/**
 * Created by katsuyagoto on 15/06/18.
 */
public class HomeFragment extends Fragment implements OnClickMainFABListener, OnRecyclerItemClickListener {

    private static final int REQUEST_CREATE_TASK_ACTIVITY = 1000;

    private OnMakeSnackbar mOnMakeSnackbar;
    private GridView mGridView;
    private DBHelper dbHelper;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnMakeSnackbar) {
            mOnMakeSnackbar = (OnMakeSnackbar) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = findById(view, R.id.gridview);
        dbHelper = new DBHelper(getContext());
        List<AllDataBean> queryAll = dbHelper.queryAll();
        AllDataAdapter adapter = new AllDataAdapter(queryAll, this);
        if (mGridView != null) {
            mGridView.setAdapter(adapter);
        }
//        RecyclerView recyclerView = findById(view, R.id.home_recycle_view);
//        recyclerView.addItemDecoration(
//                new DividerItemDecoration(FloatUtils.getDrawableResource(getActivity(), R.drawable.line)));
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * 显示Snackbar
     *
     * @param text
     * @param listener
     */
    private void showSnackbarWhenDismiss(String text, View.OnClickListener listener) {
        if (mOnMakeSnackbar != null) {
            Snackbar snackbar = mOnMakeSnackbar.onMakeSnackbar(text, Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.undo, listener);
            snackbar.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CREATE_TASK_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {

                }
                break;
        }
    }

    /**
     * Should call this method after HomeTaskListAdapter's data are changed.
     */
    private void updateHomeTaskListAdapter() {
    }

    @Override
    public void onClickMainFAB() {
//        Intent intent = new Intent(getActivity(), CreateNewTaskActivity.class);
//        startActivityForResult(intent, REQUEST_CREATE_TASK_ACTIVITY);
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

    /***********
     * EventBus回调函数
     *************/
    public void onEventMainThread(EventBusBean busBean) {


    }

    @Override
    public void onClickRecyclerItem(View v, int position) {

    }
}
