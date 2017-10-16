package fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shery.safco.R;

import java.io.File;
import java.util.ArrayList;

import adapters.GenericAdapter;
import customclasses.CommonMethod;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

/**
 * Created by shery on 12/15/2016.
 */

public class DocumentAddFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = DocumentAddFragment.class.getSimpleName();
    private Button pickPhotoBtn, saveBtn;
    private RecyclerView imageRecView;
    private GenericAdapter<Bitmap> adapter;
    private ArrayList<String> selectPhotosPath;
    private ArrayList<Bitmap> selectPhotosBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_document, container, false);
        pickPhotoBtn = (Button) view.findViewById(R.id.pickPhotoBtn);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        imageRecView = (RecyclerView) view.findViewById(R.id.imageRecView);
        pickPhotoBtn.setOnClickListener(this);
        selectPhotosPath = new ArrayList<String>();
        selectPhotosBitmap = new ArrayList<Bitmap>();
        imageRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        imageRecView.addItemDecoration(new SpacesItemDecoration(4));
        pickPhotoBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == pickPhotoBtn) {
            FilePickerBuilder.getInstance().setMaxCount(10)
                    .setActivityTheme(R.style.AppTheme3)
                    .pickPhoto(getActivity());

        }else if (view == saveBtn){


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new GenericAdapter<Bitmap>(getContext(), selectPhotosBitmap) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.image_layout, parent, false);
                view.setOnClickListener(this);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Bitmap val, int position) {
                Log.e(TAG, "onBindData " + val);
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                try {

                    itemViewHolder.imageView.setImageBitmap(val);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onItemClick(View view) {

            }
        };
        imageRecView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case FilePickerConst.REQUEST_CODE_PHOTO:

                if (resultCode == Activity.RESULT_OK && data != null) {

                    for (String path : data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS)) {
                        if (!selectPhotosPath.contains(path) && selectPhotosPath.size() < 10) {
                            selectPhotosPath.add(path);
                            File file = new File(path);
                            selectPhotosBitmap.add(CommonMethod.compressImage(file, getContext()));
                        }
                    }

                    adapter.addItems(selectPhotosBitmap);
                    adapter.notifyDataSetChanged();
                    Log.e(TAG, "Number Of Files: " + selectPhotosPath.size());
                }

        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
