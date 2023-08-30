package com.example.servein;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter2 extends FragmentStateAdapter {

    public MyAdapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new dashboard_details();
            case 1:
                return new messages();
            case 2:
                return new transactions();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Dashboard";
            case 1:
                return "Messages";
            case 2:
                return "Transaction";
            default:
                return null;

        }


}}







