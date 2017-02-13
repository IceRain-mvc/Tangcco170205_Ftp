package com.tangcco170205_ftp.db;

import com.tangcco170205_ftp.bean.AllDataBean;

/**
 * Created by Administrator on 2017/2/13.
 */

public interface Dbdao {
    void insert(AllDataBean allDataBean);
    void deleteById(String movieId);
    void updata(AllDataBean allDataBean);
    void queryAll();

}
