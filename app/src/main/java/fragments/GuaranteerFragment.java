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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.shery.safco.R;

import java.util.ArrayList;

import activities.AssetActivity;
import activities.CustomerFormActivity;
import activities.FamilyMemberActivity;
import activities.GuaranteerActivity;
import activities.LoanActivity;
import adapters.GenericAdapter;
import customclasses.CommonConst;
import customclasses.CommonMethod;
import customclasses.Customer;
import customclasses.Guaranteer;
import customclasses.MicrofinanceCustomers;
import interfaces.ICustomer;

/**
 * Created by shery on 11/24/2016.
 */

public class GuaranteerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = GuaranteerFragment.class.getSimpleName();
    private ButtonFloat addBtn;
    private ArrayList<Guaranteer> guaranteerArrayList;
    private RecyclerView recView;
    private GenericAdapter<Guaranteer> recAdapter;
    private GuaranteerActivity activity;
    private Toast toast;

    CharSequence menu[] = new CharSequence[]{"View", "Delete"};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guaranteer, container, false);
        activity = ((GuaranteerActivity) getActivity());
        addBtn = (ButtonFloat) view.findViewById(R.id.addBtn);
        recView = (RecyclerView) view.findViewById(R.id.recView);
        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        addBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        guaranteerArrayList = activity.getDbHandler().getGuaranteerData(activity.getCustomerId());
        recAdapter = new GenericAdapter<Guaranteer>(getContext(), guaranteerArrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.asset_row_item, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);

                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Guaranteer val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);

                itemViewHolder.text1.setText("Name : " + val.getFullName());
                itemViewHolder.text2.setText("NIC: " + val.getNICNumber());


            }

            @Override
            public void onItemClick(View view) {
                showMenu(getItem(Integer.valueOf(view.getTag() + "")));

            }
        };
        recView.setAdapter(recAdapter);

    }

    @Override
    public void onClick(View view) {
        if (view == addBtn) {
            if (activity.getDbHandler().groupMemberExist(activity.getCustomerId())){

                CommonMethod.alert(getContext(),
                        "Customer Contained By A Group",
                        "Guarantors Can't Be Added, Customer Contained By A Group");

            }else{
                ((GuaranteerActivity) getActivity()).moveToGuaranteerForm(null);
            }


        }
    }

    public void showDeleteAlert(final Guaranteer item){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                guaranteerArrayList.remove(item);
                recAdapter.notifyDataSetChanged();
                activity.getDbHandler().deleteGuaranteerData(item.getCustomerGuaranteerId());
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

    //TODO Guaranteer Fragment ShowMenu
    public void showMenu(final Guaranteer item) {


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
                        args.putSerializable("guaranteer", item);
                        ((GuaranteerActivity) getActivity()).moveToGuaranteerForm(args);

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

}
