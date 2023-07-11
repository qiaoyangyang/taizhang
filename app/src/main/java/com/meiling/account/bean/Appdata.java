package com.meiling.account.bean;

import java.util.ArrayList;
import java.util.List;

public class Appdata {
    public static ArrayList<BindMeituanShopBean> getBindMeituanShopBean() {
        ArrayList<BindMeituanShopBean> bindMeituanShopBeans = new ArrayList<>();
        bindMeituanShopBeans.add(new BindMeituanShopBean(1,"美团商家版","未绑定给其他系统，使用美团外卖商家版接单和打印",true));
        bindMeituanShopBeans.add(new BindMeituanShopBean(2,"其他接单系统","使用其他系统接单，如收银、运营、店铺管理等系统接单",false));

        return bindMeituanShopBeans;
    }
    public static ArrayList<GoosClassify> getGoosClassify(){
        ArrayList<GoosClassify> goosClassifies=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GoosClassify goosClassify=new GoosClassify();
            if (i==0) {
                goosClassify.setSelect(true);
            }else {
                goosClassify.setSelect(false);
            }
            goosClassify.setSortName("分类"+i);
            goosClassifies.add(goosClassify);
        }
        return goosClassifies;
    }
}
