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

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter <CategoryRecyclerViewAdapter.CategoryTypeViewHolder> {

    private static final String LOG_TAG = CategoryRecyclerViewAdapter.class.getSimpleName();
    private List<DAOTransaction.TransactionTypeDetail> mCategoryDetails;
    private LayoutInflater mInflater;

    public CategoryRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setTransactionDetails(List<DAOTransaction.TransactionTypeDetail> myllist) {
        mCategoryDetails = myllist;
    }

    public class CategoryTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView textViewAmount;
        final CategoryRecyclerViewAdapter mAdapter;

        public CategoryTypeViewHolder(View itemView, CategoryRecyclerViewAdapter adapter) {
            super(itemView);

            textViewAmount = itemView.findViewById(R.id.accountdetailsitem_amount);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        //Method from View.OnClickListener
        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "Item clicked");
            //Get the position of item that was clicked
            int mPosition = getLayoutPosition();
            Log.d(LOG_TAG, "Transaction item clicked");
            Intent intent = new Intent(v.getContext(), AlterTransactionDetailsActivity.class);
            //intent.putExtra("id_transaction", element);
            v.getContext().startActivity(intent);
            //INTENT for AccountDetails
            //mAdapter.notifyDataSetChanged();
        }
    }

    //Creates Holder based on XML
    @NonNull
    @Override
    public CategoryTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_account_details, parent, false);
        return new CategoryTypeViewHolder(mItemView, this);
    }

    //Store data in holder
    @Override
    public void onBindViewHolder(@NonNull CategoryTypeViewHolder holder, int position) {
        String mCurrentAmount = Double.toString(mCategoryDetails.get(position).trans_amount);
        holder.textViewAmount.setText(mCurrentAmount);
    }

    //Counts number of items to be displayed
    @Override
    public int getItemCount() {
        if(mCategoryDetails != null) {
            return mCategoryDetails.size();
        }
        return 0;
    }
}
