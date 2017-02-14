package com.tangcco170205_ftp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.adapter.AllDataAdapter;
import com.tangcco170205_ftp.bean.AllDataBean;
import com.tangcco170205_ftp.bean.EventBusBean;
import com.tangcco170205_ftp.db.DBHelper;
import com.tangcco170205_ftp.listener.OnClickMainFABListener;
import com.tangcco170205_ftp.listener.OnMakeSnackbar;

import java.util.List;

import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;


/**
 * Created by cuilibao on 17/02/14.
 */
public class HomeFragment extends Fragment implements OnClickMainFABListener{

    private static final int REQUEST_CREATE_TASK_ACTIVITY = 1000;

    private OnMakeSnackbar mOnMakeSnackbar;
    private GridView mGridView;
    private DBHelper dbHelper;
    private List<AllDataBean> queryAll;
    private AllDataAdapter adapter;
    private EditText searchEditText;

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

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new AllDataAdapter(queryAll);
            mGridView.setAdapter(adapter);
        }
    };
    Runnable runnableQuery = new Runnable() {
        public void run() {
            dbHelper = new DBHelper(getContext());
            queryAll = dbHelper.queryAll();
            mHandler.sendEmptyMessage(0);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = findById(view, R.id.gridview);
        searchEditText = findById(view, R.id.searchEditText);
        new Thread(runnableQuery).start();


//        RecyclerView recyclerView = findById(view, R.id.home_recycle_view);
//        recyclerView.addItemDecoration(
//                new DividerItemDecoration(FloatUtils.getDrawableResource(getActivity(), R.drawable.line)));
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initSearchListener();
    }

    private void initSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString();
                //数据库搜索
                List<AllDataBean> dataBeanList = dbHelper.queryByXXX(result);
                adapter = new AllDataAdapter(dataBeanList);
                mGridView.setAdapter(adapter);
            }
        });
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

}
