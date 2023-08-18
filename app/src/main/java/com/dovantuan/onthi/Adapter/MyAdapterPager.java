package com.dovantuan.onthi.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dovantuan.onthi.Fragments.Frag_DanhSach;
import com.dovantuan.onthi.Fragments.Frag_ThongTin;

public class MyAdapterPager extends FragmentStateAdapter {
    int soLuongPage = 2;

    public MyAdapterPager(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new Frag_DanhSach();
            case 1:
                return new Frag_ThongTin();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return soLuongPage;
    }
}
