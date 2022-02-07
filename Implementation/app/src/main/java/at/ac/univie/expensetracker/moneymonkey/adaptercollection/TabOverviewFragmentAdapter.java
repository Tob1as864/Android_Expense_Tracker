package at.ac.univie.expensetracker.moneymonkey.adaptercollection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import at.ac.univie.expensetracker.moneymonkey.fragment.TabOverviewAccountFragment;
import at.ac.univie.expensetracker.moneymonkey.fragment.TabOverviewCategoryFragment;
import at.ac.univie.expensetracker.moneymonkey.fragment.TabOverviewSummaryFragment;

public class TabOverviewFragmentAdapter extends FragmentStateAdapter {

    public TabOverviewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1: return new TabOverviewSummaryFragment();
            case 2: return new TabOverviewCategoryFragment();
        }

        return new TabOverviewAccountFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
