package at.ac.univie.expensetracker.moneymonkey.adaptercollection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.expensetracker.moneymonkey.R;
import at.ac.univie.expensetracker.moneymonkey.activity.AccountDetailsActivity;
import at.ac.univie.expensetracker.moneymonkey.activity.AlterExistingAccountTypeActivity;
import at.ac.univie.expensetracker.moneymonkey.database.entity.EntityAccountType;

public class AddNewAccountRecyclerViewAdapter extends RecyclerView.Adapter<AddNewAccountRecyclerViewAdapter.AccountTypeViewHolder> {

    private static final String LOG_TAG = AddNewAccountRecyclerViewAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<EntityAccountType> mAccountTypes;
    private List<Integer> positionSelected = new ArrayList<>();
    private EntityAccountType selectedAccountType;

    public AddNewAccountRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    //Creates Holder based on XML
    @NonNull
    @Override
    public AddNewAccountRecyclerViewAdapter.AccountTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_account_type, parent, false);
        return new AddNewAccountRecyclerViewAdapter.AccountTypeViewHolder(mItemView, this);
    }

    //Store data in holder
    @Override
    public void onBindViewHolder(@NonNull AddNewAccountRecyclerViewAdapter.AccountTypeViewHolder holder, int position) {
        if (mAccountTypes != null) {
            EntityAccountType current = mAccountTypes.get(position);
            holder.textViewAccountType.setText(current.getMAccountTypeName());
            if (positionSelected.contains(position)) {
                holder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            else {
                holder.itemView.setBackgroundColor(Color.WHITE);
            }
        }
        else {
            holder.textViewAccountType.setText("No accounts types  in database yet");
        }
    }

    //Counts number of items to be displayed
    @Override
    public int getItemCount() {
        if (mAccountTypes != null) {
            return mAccountTypes.size();
        }
        else {
            return 0;
        }
    }

    public void setAccountTypes(List<EntityAccountType> accountTypes) {
        mAccountTypes = accountTypes;
        notifyDataSetChanged();
    }

    public EntityAccountType getSelectedAccountType() {
        return selectedAccountType;
    }

    public class AccountTypeViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public final TextView textViewAccountType;
        final AddNewAccountRecyclerViewAdapter mAdapter;

        public AccountTypeViewHolder(View itemView, AddNewAccountRecyclerViewAdapter adapter) {
            super(itemView);

            itemView.setClickable(true);
            textViewAccountType = itemView.findViewById(R.id.accounttypeitem_textview_accountype);

            this.mAdapter = adapter;
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if(mAccountTypes != null) {
                int mPosition = getLayoutPosition();
                Log.d(LOG_TAG, "Account type item long clicked");
                // Set background color to gray for selected
                v.setBackgroundColor(Color.LTGRAY);

                // Save position from clicked item and notify old item about selected item
                if(positionSelected.isEmpty()){
                    positionSelected.add(mPosition);
                }
                else {
                    int oldPosition = positionSelected.get(0);
                    positionSelected.clear();
                    positionSelected.add(mPosition);
                    // Deselected old item
                    notifyItemChanged(oldPosition);
                }
                selectedAccountType = mAccountTypes.get(mPosition);
            }

            return true;
        }

        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "Account type item short clicked");
            if (mAccountTypes != null) {
                //Get the position of item that was clicked
                int mPosition = getLayoutPosition();
                EntityAccountType clickedEntityAccountType = mAccountTypes.get(mPosition);
                //INTENT for AccountDetails
                Intent intent = new Intent(v.getContext(), AlterExistingAccountTypeActivity.class);

                int putExtraAccountTypeID = clickedEntityAccountType.getMAccountTypeID();
                String putExtraAccountTypeName = clickedEntityAccountType.getMAccountTypeName();

                intent.putExtra("entity_account_type_id", putExtraAccountTypeID);
                intent.putExtra("entity_account_type_name", putExtraAccountTypeName);
                v.getContext().startActivity(intent);
            }

        }
    }
}
