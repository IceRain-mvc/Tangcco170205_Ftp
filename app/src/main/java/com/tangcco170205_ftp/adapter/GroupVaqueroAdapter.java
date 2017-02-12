package com.tangcco170205_ftp.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.listener.OnRecyclerItemClickListener;

import java.security.acl.Group;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/12.
 */
public class GroupVaqueroAdapter extends RecyclerView.Adapter<GroupVaqueroAdapter.ViewHolder>{

    private List<Group> mGroups;

    private static OnRecyclerItemClickListener sOnRecyclerItemClickListener;

    public GroupVaqueroAdapter(List<Group> groups,
                            OnRecyclerItemClickListener onRecyclerItemClickListener) {
        super();
        mGroups = groups;
        sOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_vaquero, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Group group = mGroups.get(position);
//        holder.groupNameText.setText(group.getName());
//        holder.groupDescriptionText.setText(group.getDescription());

//        if (group.isDefaultGroup()) {
//            GroupHelper.setupDefaultGroupImage(group.getName(), holder.groupImageView);
//        }
    }


    public Group getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }


    public void release() {
        sOnRecyclerItemClickListener = null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.group_image)
        ImageView groupImageView;

        @Bind(R.id.group_name)
        AppCompatTextView groupNameText;

        @Bind(R.id.group_description)
        AppCompatTextView groupDescriptionText;

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
}
