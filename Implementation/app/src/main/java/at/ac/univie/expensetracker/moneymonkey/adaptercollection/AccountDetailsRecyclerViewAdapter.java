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
import at.ac.univie.expensetracker.moneymonkey.activity.AlterTransactionDetailsActivity;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransaction;

public class AccountDetailsRecyclerViewAdapter extends RecyclerView.Adapter <AccountDetailsRecyclerViewAdapter.AccountDetailsViewHolder> {

    private static final String LOG_TAG = AccountDetailsRecyclerViewAdapter.class.getSimpleName();
    private List<DAOTransaction.TransactionAccountDetail> mAllTransactionForAccount;
    private int mAccountIDForAdapter;
    private LayoutInflater mInflater;

    public AccountDetailsRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public class AccountDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView textViewAmount;
        public final TextView textViewTransactionID;
        final AccountDetailsRecyclerViewAdapter mAdapter;

        public AccountDetailsViewHolder(View itemView, AccountDetailsRecyclerViewAdapter adapter) {
            super(itemView);

            textViewAmount = itemView.findViewById(R.id.accountdetailsitem_amount);
            textViewTransactionID = itemView.findViewById(R.id.accountdetailsitem_textview_showtransactionid);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        //Method from View.OnClickListener
        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "Transaction item clicked");

            if(mAllTransactionForAccount != null) {
                int mPosition = getLayoutPosition();
                //INTENT for TransactionDetails
                Intent intent = new Intent(v.getContext(), AlterTransactionDetailsActivity.class);
                intent.putExtra("clicked_transaction_id", mAllTransactionForAccount.get(mPosition).trans_id);
                intent.putExtra("clicked_transaction_amount", mAllTransactionForAccount.get(mPosition).trans_amount);
                intent.putExtra("clicked_transaction_category", mAllTransactionForAccount.get(mPosition).trans_type_name);
                intent.putExtra("entity_account_id", mAccountIDForAdapter);
                v.getContext().startActivity(intent);
            }
        }
    }

    //Creates Holder based on XML
    @NonNull
    @Override
    public AccountDetailsRecyclerViewAdapter.AccountDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_account_details, parent, false);
        return new AccountDetailsRecyclerViewAdapter.AccountDetailsViewHolder(mItemView, this);
    }

    //Store data in holder
    @Override
    public void onBindViewHolder(@NonNull AccountDetailsRecyclerViewAdapter.AccountDetailsViewHolder holder, int position) {
        double mCurrentAmount = mAllTransactionForAccount.get(position).trans_amount;
        int mCurrentTransactionID = mAllTransactionForAccount.get(position).trans_id;
        String mCurrentAmountString = Double.toString(mCurrentAmount);
        String mCurrentTransactionIDString = Integer.toString(mCurrentTransactionID);
        holder.textViewAmount.setText(mCurrentAmountString);
        holder.textViewTransactionID.setText(mCurrentTransactionIDString);
    }

    //Counts number of items to be displayed
    @Override
    public int getItemCount() {
        if(mAllTransactionForAccount != null) {
            return mAllTransactionForAccount.size();
        } else {
            return 0;
        }
    }

    public void setTransactionsForAccount(List<DAOTransaction.TransactionAccountDetail> FromObserverAllTransactions, int accountIDForAdapter) {
        mAllTransactionForAccount = FromObserverAllTransactions;
        mAccountIDForAdapter = accountIDForAdapter;
        notifyDataSetChanged();
    }
}
