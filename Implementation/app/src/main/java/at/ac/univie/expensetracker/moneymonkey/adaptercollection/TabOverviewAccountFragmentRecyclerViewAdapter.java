package at.ac.univie.expensetracker.moneymonkey.adaptercollection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.activity.AccountDetailsActivity;
import at.ac.univie.expensetracker.moneymonkey.activity.AlterAccountActivity;
import at.ac.univie.expensetracker.moneymonkey.activity.AlterExistingAccountTypeActivity;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;
import at.ac.univie.expensetracker.moneymonkey.utils.iterator.Iterator;
import at.ac.univie.expensetracker.moneymonkey.utils.iterator.IteratorEnvironmentHelper;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;

public class TabOverviewAccountFragmentRecyclerViewAdapter extends RecyclerView.Adapter<TabOverviewAccountFragmentRecyclerViewAdapter.DashboardViewHolder> {

    private static final String LOG_TAG = TabOverviewAccountFragmentRecyclerViewAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<DAOTransaction.AllAccountSums> mSumPerAccount;
    private IteratorEnvironmentHelper iteratorEnvironmentHelper;

    public TabOverviewAccountFragmentRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // Creates Holder based on XML
    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.item_account, parent, false);
            return new DashboardViewHolder(mItemView, this);
    }

    // Store data in holder
    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        if(mSumPerAccount == null) {
            holder.textViewAccountName.setText("No accounts  in database yet");
        } else if(position < getItemCount()-1) {
            DAOTransaction.AllAccountSums current = mSumPerAccount.get(position);
            holder.textViewAccountBalance.setText(Double.toString(current.acc_amount));
            holder.textViewAccountName.setText(current.acc_name);
            holder.textViewAccountType.setText(current.acc_type_name);
        } else {
            // Shows sum of all accounts at the end of the recyclerview
            double totalSum = 0;
            for(Iterator iterator = iteratorEnvironmentHelper.createIterator(); iterator.nextElement();) {
                DAOTransaction.AllAccountSums object = (DAOTransaction.AllAccountSums) iterator.getNextElement();
                totalSum += object.acc_amount;
            }
            holder.textViewAccountType.setText("Total");
            holder.textViewAccountName.setText("Amount");
            holder.textViewAccountBalance.setText(Double.toString(totalSum));
        }
    }

    // Counts number of items to be displayed
    @Override
    public int getItemCount() {
        if (mSumPerAccount != null) {
            return mSumPerAccount.size()+1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position<(getItemCount()-1)) {
            return 1;
        }
        else {
            return 2;
        }

    }

    public void setSumPerAccount(List<DAOTransaction.AllAccountSums> sumPerAccount) {
        mSumPerAccount = sumPerAccount;
        iteratorEnvironmentHelper = new IteratorEnvironmentHelper(sumPerAccount);
        notifyDataSetChanged();
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public final TextView textViewAccountName;
        public final TextView textViewAccountType;
        public final TextView textViewAccountBalance;
        final TabOverviewAccountFragmentRecyclerViewAdapter mAdapter;

        public DashboardViewHolder(View itemView, TabOverviewAccountFragmentRecyclerViewAdapter adapter) {
            super(itemView);

            itemView.setClickable(true);
            textViewAccountName = itemView.findViewById(R.id.accountitem_accountname);
            textViewAccountType = itemView.findViewById(R.id.accountitem_accountype);
            textViewAccountBalance = itemView.findViewById(R.id.accountitem_balance);

            this.mAdapter = adapter;
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }



        // Method from View.OnClickListener
        @Override
        public void onClick(View v) {
            if (mSumPerAccount != null) {
                //Get the position of item that was clicked
                int mPosition = getLayoutPosition();
                if(mPosition == mSumPerAccount.size()) {
                    Log.d(LOG_TAG, "Account item clicked");
                } else {
                    Log.d(LOG_TAG, "Account item clicked");
                    int clickedEntityAccountID = mSumPerAccount.get(mPosition).acc_id;
                    String clickedEntityAccountName = mSumPerAccount.get(mPosition).acc_name;

                    //INTENT for AccountDetails
                    Intent intent = new Intent(v.getContext(), AccountDetailsActivity.class);
                    intent.putExtra("entity_account_id", clickedEntityAccountID);
                    intent.putExtra("entity_account_name", clickedEntityAccountName);
                    v.getContext().startActivity(intent);
                    //mAdapter.notifyDataSetChanged();
                }
            }

        }

        @Override
        public boolean onLongClick(View v) {
            if (mSumPerAccount != null) {
                //Get the position of item that was clicked
                int mPosition = getLayoutPosition();

                if(mPosition == mSumPerAccount.size()) {
                    Log.d(LOG_TAG, "Account type item long clicked");
                } else {
                    Log.d(LOG_TAG, "Account type item long clicked");
                    int clickedEntityAccountID = mSumPerAccount.get(mPosition).acc_id;
                    String clickedEntityAccountName = mSumPerAccount.get(mPosition).acc_name;
                    //INTENT for AccountDetails
                    Intent intent = new Intent(v.getContext(), AlterAccountActivity.class);
                    intent.putExtra("entity_account_id", clickedEntityAccountID);
                    intent.putExtra("entity_account_name", clickedEntityAccountName);
                    v.getContext().startActivity(intent);
                }
            }
            return true;
        }
    }
}
