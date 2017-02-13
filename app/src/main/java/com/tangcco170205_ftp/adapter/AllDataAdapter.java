package com.tangcco170205_ftp.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.bean.AllDataBean;
import com.tangcco170205_ftp.listener.OnRecyclerItemClickListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/12.
 */
public class AllDataAdapter extends BaseAdapter{

    private List<AllDataBean> mData;

    private static OnRecyclerItemClickListener sOnRecyclerItemClickListener;

    public AllDataAdapter(List<AllDataBean> groups,
                          OnRecyclerItemClickListener onRecyclerItemClickListener) {
        super();
        mData = groups;
        sOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }


    @Override
    public int getCount() {
        return  12;
    }

    public AllDataBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AllDataBean allDataBean = mData.get(position);
        holder.groupNameText.setText(allDataBean.getTitle());
        holder.groupActorText.setText(allDataBean.getActor());
        holder.groupDescriptionText.setText(allDataBean.getDirector());

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//是否添加到内部缓存中
                .cacheOnDisk(false)//是否添加到磁盘缓存中
                .displayer(new RoundedBitmapDisplayer(10))//圆角
//                .displayer(new CircleBitmapDisplayer())//圆形
                .showImageOnFail(R.mipmap.liufei)
                .showImageOnLoading(R.mipmap.liufei)
                .bitmapConfig(Bitmap.Config.RGB_565)//2被压缩
                .build();
        imageLoader.displayImage(allDataBean.getPic(),holder.groupImageView,options,new LoderImageListener());

        return convertView;
    }


    public void release() {
        sOnRecyclerItemClickListener = null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.movImage6)
        ImageView groupImageView;

        @Bind(R.id.movNameText6)
        TextView groupNameText;

        @Bind(R.id.movActorText6)
        TextView groupActorText;

        @Bind(R.id.movAreaText6)
        TextView groupDescriptionText;

        public ViewHolder(final View v) {
            super(v);
            v.setOnClickListener(this);
            ButterKnife.bind(this, v);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                sOnRecyclerItemClickListener.onClickRecyclerItem(view, position);
            }
        }
    }

    class LoderImageListener extends SimpleImageLoadingListener {
        //淡入动画
        List<String> list = Collections.synchronizedList(new LinkedList<String>());
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            if (loadedImage != null) {
                //第一次加载图片
                if (!list.contains(imageUri)) {

                    AlphaAnimation fadeImage = new AlphaAnimation(0.0F, 1.0F);
                    fadeImage.setDuration(500);
                    view.startAnimation(fadeImage);
                    list.add(imageUri);
                }
            }
        }
    }
}
