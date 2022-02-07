package at.ac.univie.expensetracker.moneymonkey.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.activity.AddNewAccountActivity;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.TabOverviewAccountViewModel;
import at.ac.univie.expensetracker.moneymonkey.adaptercollection.TabOverviewAccountFragmentRecyclerViewAdapter;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;

public class TabOverviewAccountFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TabOverviewAccountFragmentRecyclerViewAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private TabOverviewAccountViewModel mTabOverviewAccountViewModel;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TabOverviewAccountFragment() {
        // Required empty public constructor
    }

    public static TabOverviewAccountFragment newInstance(String param1, String param2) {
        TabOverviewAccountFragment fragment = new TabOverviewAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View Model Handling
        mTabOverviewAccountViewModel = new ViewModelProvider(this).get(TabOverviewAccountViewModel.class);
        mTabOverviewAccountViewModel.getSumPerAccount().observe(getViewLifecycleOwner(), new Observer<List<DAOTransaction.AllAccountSums>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransaction.AllAccountSums> entitySumPerAccount) {
                mAdapter.setSumPerAccount(entitySumPerAccount);
            }
        });

        //Create rootView to access properties
        View rootView = inflater.inflate(R.layout.fragment_tab_overview_account, container, false);

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.activitydashboard_floatingactionbutton_addaccounts);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call new activity
                Intent intent = new Intent(rootView.getContext(), AddNewAccountActivity.class);
                startActivity(intent);
            }
        });

        //Start the recyclerview
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.dashboard_recyclerview);
        mAdapter = new TabOverviewAccountFragmentRecyclerViewAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return rootView;
    }
}