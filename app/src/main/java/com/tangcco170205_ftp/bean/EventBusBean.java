package com.tangcco170205_ftp.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */

public class EventBusBean {
    private List<AllDataBean> allDataBeanList;

    public EventBusBean(List<AllDataBean> allDataBeanList) {
        this.allDataBeanList = allDataBeanList;
    }

    public List<AllDataBean> getAllDataBeanList() {
        return allDataBeanList;
    }

    public void setAllDataBeanList(List<AllDataBean> allDataBeanList) {
        this.allDataBeanList = allDataBeanList;
    }
}
