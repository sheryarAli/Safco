package fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.shery.safco.R;

import java.util.ArrayList;

import activities.AssetActivity;
import activities.GuaranteerActivity;
import adapters.GenericAdapter;
import customclasses.Asset;
import customclasses.CommonConst;
import customclasses.Customer;
import customclasses.Guaranteer;
import customclasses.MicrofinanceCustomers;
import interfaces.ICustomer;

/**
 * Created by shery on 11/24/2016.
 */

public class AssetFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = AssetFragment.class.getSimpleName();
    private ButtonFloat addBtn;
    private ArrayList<Asset> assetArrayList;
    private RecyclerView recView;
    private GenericAdapter<Asset> recAdapter;
    private AssetActivity activity;

    CharSequence menu[] = new CharSequence[]{"View", "Delete"};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset, container, false);
        activity = ((AssetActivity)getActivity());
        addBtn = (ButtonFloat) view.findViewById(R.id.addBtn);
        recView = (RecyclerView) view.findViewById(R.id.recView);
        addBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        assetArrayList =  activity.getDbHandler().getAssetData(activity.getCustomerId());
        recAdapter = new GenericAdapter<Asset>(getContext(), assetArrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.asset_row_item, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);

                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Asset val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);

                itemViewHolder.text1.setText("Asset: " + val.getAssetName());
                itemViewHolder.text2.setText("Qunatity: " + val.getAssetQuantity());
                itemViewHolder.text3.setText("Value: " + val.getAssetValue());

            }

            @Override
            public void onItemClick(View view) {
                showMenu(getItem(Integer.valueOf(view.getTag()+"")));
            }
        };
        recView.setAdapter(recAdapter);

    }

    @Override
    public void onClick(View view) {
        if (view==addBtn){
            Bundle args = new Bundle();
            args.putString(CommonConst.VIEW_TYPE, "new");
            ((AssetActivity)getActivity()).moveToAssetForm(args);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView text1, text2, text3;


        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            text3 = (TextView) itemView.findViewById(R.id.text3);
        }
    }

    public void showDeleteAlert(final Asset item){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.getDbHandler().deleteAssetDataById(item.getCustomerAssetId());
                assetArrayList.remove(item);
                recAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    //TODO Asset Fragment ShowMenu
    public void showMenu(final Asset item) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Action");
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                Log.e(TAG, "menu: " + which);
                switch (which) {
                    case 0:
                        Bundle args = new Bundle();
                        args.putSerializable("asset", item);
                        ((AssetActivity)getActivity()).moveToAssetForm(args);

                        break;


                    case 1:
                        Log.e(TAG, "onClick: Delete Customer....");
                        showDeleteAlert(item);
                        break;

                }

                Log.e(TAG, "option: " + which);

            }
        });
        builder.show();
    }

}
