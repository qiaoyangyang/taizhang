package com.meiling.oms.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.meiling.common.GlideApp;

import java.util.ArrayList;
import java.util.List;

public final class ImagePagerAdapter extends PagerAdapter
        implements View.OnClickListener {

    private final Activity mActivity;
    private final List<String> mData;

    public ImagePagerAdapter(Activity activity, List<String> data) {
        mActivity = activity;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView view = new PhotoView(mActivity);

        view.setOnClickListener(this);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        GlideApp.with(mActivity)
                .load(mData.get(position))
                .into(view);


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View v) {
        // 单击图片退出当前的 Activity
        if (!mActivity.isFinishing()) {
            mActivity.finish();
        }
    }
}
