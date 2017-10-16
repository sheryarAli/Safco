package fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.shery.safco.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import activities.GroupActivity;
import adapters.GenericAdapter;
import customclasses.CommonMethod;
import customclasses.EndlessRecyclerViewScrollListener;
import customclasses.Group;
import databases.DBHandler;
import preferences.LocalStoragePreferences;

/**
 * Created by shery on 11/15/2016.
 */

public class GroupFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = GroupFragment.class.getSimpleName();
    private DatePickerDialog mDatePickerDialog;
    private String timeStamp;
    private Date curDate;
    private SimpleDateFormat dateFormater;
    private DBHandler dbHandler;
    private ArrayList<Group> groupList = new ArrayList<>();
    private ButtonFloat addBtn;
    private Button cancelBtn, searchBtn;
    private TextView availabilityText;
    private RecyclerView recView;
    private GenericAdapter<Group> recAdapter;
    private EditText searchFromDateEdt, searchToDateEdt;
    private LinearLayoutManager linearLayoutManager;
    private LocalStoragePreferences preferences;
    private int searchQueryType = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group, container, false);
        dbHandler = new DBHandler(getContext());
        addBtn = (ButtonFloat) v.findViewById(R.id.addBtn);
        recView = (RecyclerView) v.findViewById(R.id.recView);
        cancelBtn = (Button) v.findViewById(R.id.cancelBtn);
        searchBtn = (Button) v.findViewById(R.id.searchBtn);
        availabilityText = (TextView) v.findViewById(R.id.availabilityText);
        searchFromDateEdt = (EditText) v.findViewById(R.id.searchFromDateEdt);
        searchToDateEdt = (EditText) v.findViewById(R.id.searchToDateEdt);
        Typeface font = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/fontawesome-webfont.ttf");
        cancelBtn.setTypeface(font);
        searchBtn.setTypeface(font);
        linearLayoutManager = new LinearLayoutManager(getContext());
        searchFromDateEdt.setOnClickListener(this);
        searchToDateEdt.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        preferences = new LocalStoragePreferences(getContext());

        searchFromDateEdt.setText("");
        searchToDateEdt.setText("");
        recView.setLayoutManager(linearLayoutManager);

        recAdapter = new GenericAdapter<Group>(getContext(), groupList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.asset_row_item, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);

                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Group val, int position) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.cardView.setTag(position);

                itemViewHolder.text1.setText("Group Id : " + val.getGroupId());
                itemViewHolder.text2.setText("Group Name : " + val.getGroup_Name());


            }

            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(new Intent(getActivity(), GroupActivity.class));
                intent.putExtra("group", getItem(Integer.valueOf(view.getTag() + "")));
                getActivity().startActivity(intent);
            }
        };

        recView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.e(TAG, "onLoadMore: " + page);
                loadMore(searchQueryType);

            }
        });
        recView.setAdapter(recAdapter);
        loadMore(searchQueryType);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void loadMore(int i) {
        Log.e(TAG, "loadMore: searchQueryType: " + i);
        int lastIndexId = groupList.size() == 0 ? Integer.MAX_VALUE : groupList.get(groupList.size() - 1).getGroupId();
        switch (i) {
            case 0:
                groupList.addAll(dbHandler.getRecentGroupData(lastIndexId));
                break;
            case 1:
                groupList.addAll(dbHandler.getGroupDataByDate(searchFromDateEdt.getText().toString(), searchToDateEdt.getText().toString(), lastIndexId));
                break;
        }
        recView.post(new Runnable() {
            @Override
            public void run() {
                if (groupList.size() == 0) {
                    availabilityText.setVisibility(View.VISIBLE);
                    recView.setVisibility(View.GONE);
                } else {
                    availabilityText.setVisibility(View.GONE);
                    recView.setVisibility(View.VISIBLE);

                }
                recAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == addBtn) {

            if (!preferences.isSyncedDown()){
                CommonMethod.alert(getContext(), "Alert", "Please download prerequisite data to continue","Ok");
            }else {
                getActivity().startActivity(new Intent(getActivity(), GroupActivity.class));
            }


        } else if (view == searchFromDateEdt) {
            CommonMethod.pickDate(getContext(), searchFromDateEdt);
        } else if (view == searchToDateEdt) {
            CommonMethod.pickDate(getContext(), searchToDateEdt);
        }else if (view == cancelBtn) {
            searchFromDateEdt.setText("");
            searchToDateEdt.setText("");
            groupList.clear();
            searchQueryType = 0;
            loadMore(searchQueryType);

        }else if(view == searchBtn){
            if (validateUI()){
                groupList.clear();
                searchQueryType = 1;
                loadMore(searchQueryType);
            }

        }

    }

    private boolean validateUI() {
        if (CommonMethod.checkEmpty(searchFromDateEdt)) {
            searchFromDateEdt.setError("Please Select Any Date");
            return false;
        }
        if (CommonMethod.checkEmpty(searchToDateEdt)) {
            searchToDateEdt.setError("Please Select Any Date");
            return false;
        }
        return true;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView text1, text2, text3;


        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            text3 = (TextView) itemView.findViewById(R.id.text3);
            /*text2.setVisibility(View.INVISIBLE);*/
            text3.setVisibility(View.INVISIBLE);
        }
    }
}
