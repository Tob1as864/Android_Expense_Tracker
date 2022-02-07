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
import at.ac.univie.expensetracker.moneymonkey.activity.AlterCategoryActivity;
import at.ac.univie.expensetracker.moneymonkey.activity.CategoryActivity;
import at.ac.univie.expensetracker.moneymonkey.database.dao.DAOTransactionType;

public class TabOverviewCategoryFragmentRecyclerViewAdapter extends RecyclerView.Adapter<TabOverviewCategoryFragmentRecyclerViewAdapter.FragmentThreeViewHolder>{

    private static final String LOG_TAG = TabOverviewCategoryFragmentRecyclerViewAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<DAOTransactionType.ActiveTransactionNames> mCategoryName;

    public TabOverviewCategoryFragmentRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TabOverviewCategoryFragmentRecyclerViewAdapter.FragmentThreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_category, parent, false);
        return new TabOverviewCategoryFragmentRecyclerViewAdapter.FragmentThreeViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TabOverviewCategoryFragmentRecyclerViewAdapter.FragmentThreeViewHolder holder, int position) {
        if (mCategoryName != null) {
            DAOTransactionType.ActiveTransactionNames current = mCategoryName.get(position);
            holder.textViewTypeName.setText(current.transaction_type_name);
        }
        else {
            holder.textViewTypeName.setText(R.string.taboverviewfragmentthreerecyclerviewadapter_onbindviewholder_notransactiontype);
        }
    }

    @Override
    public int getItemCount() {
        if (mCategoryName != null) {
            return mCategoryName.size();
        }
        else {
            return 0;
        }
    }

    public void setTransactionType(List<DAOTransactionType.ActiveTransactionNames> transactionTypes) {
        mCategoryName = transactionTypes;
        notifyDataSetChanged();
    }

    public class FragmentThreeViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public final TextView textViewTypeName;
        final TabOverviewCategoryFragmentRecyclerViewAdapter mAdapter;

        public FragmentThreeViewHolder (View itemView, TabOverviewCategoryFragmentRecyclerViewAdapter adapter) {
            super(itemView);

            textViewTypeName = itemView.findViewById(R.id.transactiontypeitem_typename);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        //Method from View.OnClickListener
        @Override
        public void onClick(View v) {
            //Get the position of item that was clicked
            v.refreshDrawableState();
            int mPosition = getLayoutPosition();
            //Get name of mPosition
            int element = mCategoryName.get(mPosition).cat_id;
            String categoryName = mCategoryName.get(mPosition).transaction_type_name;
            Log.d(LOG_TAG, "Category item clicked");

            //INTENT for CategoryDetails
            Intent intent = new Intent(v.getContext(), CategoryActivity.class);
            intent.putExtra("id_category", element);
            intent.putExtra("name_category", categoryName);
            v.getContext().startActivity(intent);
            //mAdapter.notifyDataSetChanged();

        }

        //Method for opening Delete/Update
        @Override
        public boolean onLongClick(View v) {
            if (mCategoryName != null) {
                //Get the position of item that was clicked
                int mPosition = getLayoutPosition();
                //Get name of mPosition??

                if(mPosition == mCategoryName.size()) {
                    Log.d(LOG_TAG, "Transaction type item long clicked");
                } else {
                    Log.d(LOG_TAG, "Transaction type item long clicked");
                    int element = mCategoryName.get(mPosition).cat_id;
                    String categoryName = mCategoryName.get(mPosition).transaction_type_name;

                    //INTENT for CategoryDetails
                    Intent intent = new Intent(v.getContext(), AlterCategoryActivity.class);
                    intent.putExtra("id_category", element);
                    intent.putExtra("name_category", categoryName);
                    v.getContext().startActivity(intent);
                }
            }
            return true;
        }
    }
}
