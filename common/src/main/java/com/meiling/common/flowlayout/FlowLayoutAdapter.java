package com.meiling.common.flowlayout;


import android.content.Context;
import android.view.View;

    public abstract class FlowLayoutAdapter {
    private FlowLayout flowLayout;

    protected abstract View createView(Context context, FlowLayout flowLayout, int position);

    public abstract int getItemCount();

    public abstract Object getItem(int position);

    protected void setFlowLayout(FlowLayout flowLayout) {
        this.flowLayout = flowLayout;
    }

    public void notifyChange() {
        flowLayout.requestLayout();
    }

}
