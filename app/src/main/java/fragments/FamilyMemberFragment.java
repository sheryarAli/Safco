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
import activities.FamilyMemberActivity;
import adapters.GenericAdapter;
import customclasses.Asset;
import customclasses.CommonConst;
import customclasses.FamilyMember;
import customclasses.MicrofinanceCustomers;
import interfaces.ICustomer;

/**
 * Created by shery on 11/24/2016.
 */

public class FamilyMemberFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = GuaranteerFragment.class.getSimpleName();
    private ButtonFloat addBtn;
    private RecyclerView recView;
    private ArrayList<FamilyMember> familyMemberArrayList;
    private GenericAdapter<FamilyMember> recAdapter;
    private FamilyMemberActivity activity;
    CharSequence menu[] = new CharSequence[]{"View", "Delete"};


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_member, container, false);
        addBtn = (ButtonFloat) view.findViewById(R.id.addBtn);
        activity = ((FamilyMemberActivity) getActivity());
        recView = (RecyclerView) view.findViewById(R.id.recView);
        addBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        familyMemberArrayList = activity.getDbHandler().getFamilyMemberData(activity.getCustomerId());
        recAdapter = new GenericAdapter<FamilyMember>(getContext(), familyMemberArrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.asset_row_item, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);

                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, FamilyMember val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);

                itemViewHolder.text1.setText("Name : " + val.getFullName());
                itemViewHolder.text2.setText("NIC: " + val.getNICNumber());
                itemViewHolder.text3.setText("Relation: " + val.getRelationshipWithCustomer());

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
        if (view == addBtn) {
            Bundle args = new Bundle();
            args.putString(CommonConst.VIEW_TYPE, "new");
            ((FamilyMemberActivity)getActivity()).moveToFamilyMemberForm(args);
        }

    }


    public void showDeleteAlert(final FamilyMember item){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.getDbHandler().deleteFamilyMemberDataById(item.getCustomerFamilyMemberId());
                familyMemberArrayList.remove(item);
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

    //TODO Family Member Fragment ShowMenu
    public void showMenu(final FamilyMember item) {


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
                        args.putSerializable("family_member", item);
                        ((FamilyMemberActivity)getActivity()).moveToFamilyMemberForm(args);
                        break;
                    case 1:
                        showDeleteAlert(item);
                        break;

                }

                Log.e(TAG, "option: " + which);

            }
        });
        builder.show();
    }

}
