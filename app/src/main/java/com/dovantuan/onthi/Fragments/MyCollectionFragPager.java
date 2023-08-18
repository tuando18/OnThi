package com.dovantuan.onthi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.dovantuan.onthi.Adapter.MyAdapterPager;
import com.dovantuan.onthi.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MyCollectionFragPager extends Fragment {
    MyAdapterPager adapterPager;
    ViewPager2 pager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_frag_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Tạo pager
        adapterPager = new MyAdapterPager(this);
        pager2 = view.findViewById(R.id.pager2_demo);
        pager2.setAdapter(adapterPager);

        //Tạo TAB
        TabLayout tabLayout = view.findViewById(R.id.tab_01);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Danh Sách");
                        tab.setIcon(android.R.drawable.ic_input_add);
                        break;
                    case 1:
                        tab.setText("Thông tin");
                        tab.setIcon(android.R.drawable.ic_delete);
                        break;
                }
            }
        });
        mediator.attach();

    }


}
