package com.steven.android32_expandablerecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steven.android32_expandablerecyclerview.R;
import com.steven.android32_expandablerecyclerview.decoration.MyItemAnimator;
import com.steven.android32_expandablerecyclerview.model.ChildModel;
import com.steven.android32_expandablerecyclerview.model.GroupModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by StevenWang on 16/6/17.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context = null;
    private List<Object> list = null;
    private LayoutInflater mInflater = null;
    public static final int GROUP = 0, CHILD = 1;
    private RecyclerView recyclerView;
    private MyItemAnimator animator = null;

    public MyAdapter(Context context, List<Object> list, RecyclerView recyclerView ,MyItemAnimator animator) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        this.animator = animator;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = list.get(position);
        if (obj instanceof GroupModel) {
            return GROUP;
        } else if (obj instanceof ChildModel) {
            return CHILD;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case GROUP:
                view = mInflater.inflate(R.layout.item_recyclerview_group, parent, false);

                //设置Group的item的点击监听事件
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = recyclerView.getChildAdapterPosition(v);
                        GroupModel group = (GroupModel) list.get(position);
                        if (!group.isExpand()) {
                            group.setIsExpand(true);
                            animator.setXY(v.getLeft() , v.getTop());
                            list.addAll(position + 1, group.getChildModels());
                            notifyItemRangeInserted(position + 1, group.getChildModels().size());
                        } else {
                            group.setIsExpand(false);
                            animator.setXY(v.getLeft() , v.getTop());
                            list.removeAll(group.getChildModels());
                            notifyItemRangeRemoved(position + 1, group.getChildModels().size());
                        }
                    }
                });
                return new ViewHolderGroup(view);
            case CHILD:
                view = mInflater.inflate(R.layout.item_recyclerview_child, parent, false);
                return new ViewHolderChild(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderGroup) {
            GroupModel model = (GroupModel) list.get(position);
            ((ViewHolderGroup) holder).textView_item_group.setText(model.getTitle());
        } else if (holder instanceof ViewHolderChild) {
            ChildModel model = (ChildModel) list.get(position);
            ((ViewHolderChild) holder).textView_item_child.setText(model.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderGroup extends RecyclerView.ViewHolder {
        @Bind(R.id.textView_item_group)
        TextView textView_item_group;

        public ViewHolderGroup(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderChild extends RecyclerView.ViewHolder {
        @Bind(R.id.textView_item_child)
        TextView textView_item_child;

        public ViewHolderChild(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
