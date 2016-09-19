package com.steven.android32_expandablerecyclerview.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.steven.android32_expandablerecyclerview.R;
import com.steven.android32_expandablerecyclerview.adapter.MyAdapter;
import com.steven.android32_expandablerecyclerview.decoration.MyItemAnimator;
import com.steven.android32_expandablerecyclerview.decoration.SpacesItemDecoration;
import com.steven.android32_expandablerecyclerview.model.ChildModel;
import com.steven.android32_expandablerecyclerview.model.GroupModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private Context mContext = this;
    private List<Object> totalList = new ArrayList<>();
    private MyAdapter adapter = null;

    @Bind(R.id.recyclerView_main)
    RecyclerView recyclerView_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            GroupModel group = new GroupModel("group_" + i);
            totalList.add(group);
            for (int j = 0; j < 9; j++) {
                group.getChildModels().add(new ChildModel("child_" + j));
            }
        }
    }

    private void initView() {
        ButterKnife.bind(this);
        //1、设置固定高度
        recyclerView_main.setHasFixedSize(true);
        //2、设置分割线
        recyclerView_main.addItemDecoration(new SpacesItemDecoration(5));
        //3、设置动画效果
        MyItemAnimator animator = new MyItemAnimator();
        recyclerView_main.setItemAnimator(animator);

        //4、设置布局管理器
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        //5、给布局管理器设置列的合并数量
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == MyAdapter.GROUP) {
                    return 3;
                }
                return 1;
            }
        });
        recyclerView_main.setLayoutManager(manager);
        //6、设置适配器
        adapter = new MyAdapter(mContext, totalList ,recyclerView_main , animator);
        recyclerView_main.setAdapter(adapter);
    }
}
