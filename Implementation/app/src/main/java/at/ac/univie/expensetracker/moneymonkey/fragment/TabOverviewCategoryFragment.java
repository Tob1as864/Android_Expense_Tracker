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
import at.ac.univie.expensetracker.moneymonkey.activity.AddNewCategoryActivity;
import at.ac.univie.expensetracker.moneymonkey.viewmodel.TabOverviewAccountViewModel;
import at.ac.univie.expensetracker.moneymonkey.adaptercollection.TabOverviewCategoryFragmentRecyclerViewAdapter;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;

public class TabOverviewCategoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TabOverviewCategoryFragmentRecyclerViewAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private TabOverviewAccountViewModel mTabOverviewAccountViewModel;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TabOverviewCategoryFragment() {
        // Required empty public constructor
    }

    public static TabOverviewCategoryFragment newInstance(String param1, String param2) {
        TabOverviewCategoryFragment fragment = new TabOverviewCategoryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View Model Handling
        mTabOverviewAccountViewModel = new ViewModelProvider(this).get(TabOverviewAccountViewModel.class);
        mTabOverviewAccountViewModel.getTransactionTypeName().observe(getViewLifecycleOwner(), new Observer<List<DAOTransactionType.ActiveTransactionNames>>() {
            @Override
            public void onChanged(@Nullable List<DAOTransactionType.ActiveTransactionNames> entityTransactionTypes) {
                mAdapter.setTransactionType(entityTransactionTypes);
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_tab_overview_category, container, false);

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fragmentthreeaccountdetails_floatingactionbutton_addtransactiontype);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call new activity
                Intent intent = new Intent(rootView.getContext(), AddNewCategoryActivity.class);
                startActivity(intent);
            }
        });

        //Start the recyclerview
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragementthreetransactiontypes_recyclerview);
        mAdapter = new TabOverviewCategoryFragmentRecyclerViewAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return rootView;
    }
}