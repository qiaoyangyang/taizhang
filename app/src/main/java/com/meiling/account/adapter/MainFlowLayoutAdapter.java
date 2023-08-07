package com.meiling.account.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;


import com.meiling.account.R;
import com.meiling.account.bean.Spezifikation;
import com.meiling.common.flowlayout.FlowLayout;
import com.meiling.common.flowlayout.FlowLayoutAdapter;
import com.meiling.common.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class MainFlowLayoutAdapter extends FlowLayoutAdapter {
    private List<Spezifikation> dataList;
    private int mCheckedPosition = -1;
    public boolean iscompile = false;
    private FlowLayout.OnItemvClickListener onItemvClickListener;


    public MainFlowLayoutAdapter(List<Spezifikation> dataList) {
        this.dataList = dataList;
    }

    public MainFlowLayoutAdapter() {

    }

    public FlowLayout.OnItemvClickListener getOnItemvClickListener() {
        return onItemvClickListener;
    }



    public void setiscompile(Boolean iscompile){
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setIsselect(iscompile);
        }
        notifyChange();
    }


    public void setNewData(List<Spezifikation> datas) {
        if (datas != null) {
            if (this.dataList == null) {
                this.dataList = new ArrayList<>();
            } else {
                this.dataList.clear();
            }
            this.dataList.addAll(datas);
            notifyChange();
        }
    }

    public void addData(List<Spezifikation> datas) {
        if (datas != null && dataList != null) {
            this.dataList.addAll(datas);
            notifyChange();
        }
    }

    @Override
    protected View createView(Context context, FlowLayout flowLayout, int position) {
        Spezifikation Spezifikation = dataList.get(position);
        View view = flowLayout.inflate(context, R.layout.item_main, null);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText(Spezifikation.getGoodsSpecsValus());
        if (Spezifikation.getIsselect()){
            tv_content.setBackgroundResource(R.drawable.bg_data_time_ture);
            tv_content.setTextColor(Color.parseColor("#FE4B48"));
        }else {
            tv_content.setBackgroundResource(R.drawable.bg_fase);
            tv_content.setTextColor(Color.parseColor("#404040"));
        }

        return view;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (ListUtils.isEmpty(dataList)) return null;
        return dataList.get(position);
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
