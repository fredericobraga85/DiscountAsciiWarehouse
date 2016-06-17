package com.discount_ascii_warehouse.app.asciilist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.utils.AsciiGridCalculator;
import com.discount_ascii_warehouse.app.utils.MeasureUtils;
import com.discount_ascii_warehouse.app.utils.ScreenUtils;
import com.discount_ascii_warehouse.discountasciiwarehouseapp.R;

import java.text.DecimalFormat;
import java.util.List;


public class AsciiListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public interface  AsciiAdapterListener
    {
        void onClickAscii(Ascii ascii);
    }

    private static final int ASCII_VIEW_TYPE = 100;
    private static final int LOADING_VIEW_TYPE = 101;
    private static final int LOADING_VIEW = 1;
    private boolean shouldShowLoading = false;

    private Context context;
    private List<Ascii> asciiList;
    AsciiAdapterListener listener;

    public AsciiListAdapter(Context context , List<Ascii> asciiList, AsciiAdapterListener listener)
    {
        this.context = context;
        this.asciiList = asciiList;
        this.listener = listener;
    }

    public List<Ascii> getAsciiList() {
        return asciiList;
    }

    @Override
    public int getItemCount() {
        return asciiList.size() + LOADING_VIEW; //(shouldShowLoading ? 1 : 0) ;
    }

    @Override
    public int getItemViewType(int position) {

//        if(shouldShowLoading && isLastPosition(position))
        if(isLastPosition(position))
        {
            return LOADING_VIEW_TYPE;
        }

        return ASCII_VIEW_TYPE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == ASCII_VIEW_TYPE) {

            View vCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ascii_cardview, parent, false);

            AsciiViewHolder vh = new AsciiViewHolder(vCardView);
            vh.tvTags  = (TextView) vCardView.findViewById(R.id.tvTags);
            vh.tvFace  = (TextView) vCardView.findViewById(R.id.tvFace);
            vh.tvPrice = (TextView) vCardView.findViewById(R.id.tvPrice);

            return vh;
        }
        else
        {
            View vLoading = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_view_small, parent, false);

            LoadinViewHolder vh = new LoadinViewHolder(vLoading);
            vh.flProgress = (FrameLayout) vLoading.findViewById(R.id.flProgressBar);
            vh.progressBar = (ProgressBar) vLoading.findViewById(R.id.progressbar);

            return vh;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AsciiViewHolder) {

            AsciiViewHolder asciiViewHolder = (AsciiViewHolder) holder;

            Ascii ascii = asciiList.get(position);

            asciiViewHolder.ascii = ascii;
            asciiViewHolder.tvTags.setText(ascii.getTagsTitle());
            asciiViewHolder.tvFace.setText(ascii.getFace());
            asciiViewHolder.tvFace.setTextSize(ascii.getSize());
            asciiViewHolder.tvPrice.setText(ascii.convertPriceToDollarText());

        }
        else
        {
            LoadinViewHolder loadingViewHolder = (LoadinViewHolder) holder;
            if(shouldShowLoading) {
                loadingViewHolder.flProgress.setVisibility(View.VISIBLE);
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
            }
            else
            {
                loadingViewHolder.flProgress.setVisibility(View.GONE);
                loadingViewHolder.progressBar.setVisibility(View.GONE);
            }

        }
    }



    @Override
    public long getItemId(int position) {
        return Long.getLong(asciiList.get(position).getId());
    }


    public void showLoading() {

        shouldShowLoading = true;
        notifyItemChanged(this.asciiList.size());
    }

    public void removeLoading() {

        shouldShowLoading = false;
        notifyItemChanged(this.asciiList.size());
    }

    private boolean isLastPosition(int position) {

        if(position == asciiList.size())
        {
            return true;
        }

        return false;
    }

    public void swap(List<Ascii> asciiList) {
        this.asciiList.clear();
        this.asciiList.addAll(asciiList);
        notifyDataSetChanged();
    }

    public void refresh(List<Ascii> asciiList) {

        this.asciiList.addAll(asciiList);
        notifyDataSetChanged();

    }



     class AsciiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         Ascii ascii;
         TextView tvTags;
         TextView tvFace;
         TextView tvPrice;

        public AsciiViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onClickAscii(ascii);

        }
    }

    class LoadinViewHolder extends RecyclerView.ViewHolder  {

        FrameLayout flProgress;
        ProgressBar progressBar;

        public LoadinViewHolder(View view) {
            super(view);
        }


    }
}
