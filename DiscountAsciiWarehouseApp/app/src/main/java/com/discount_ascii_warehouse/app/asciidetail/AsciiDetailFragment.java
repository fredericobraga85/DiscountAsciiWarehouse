package com.discount_ascii_warehouse.app.asciidetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.discount_ascii_warehouse.app.asciilist.AsciiListActivity;
import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.utils.AppProperties;
import com.discount_ascii_warehouse.app.utils.DialogUtils;
import com.discount_ascii_warehouse.discountasciiwarehouseapp.R;


public class AsciiDetailFragment extends Fragment implements AsciiDetailContract.View {

    private AsciiDetailContract.UserActionsListener asciiDetailPresenter;

    Ascii ascii;

    TextView tvTags;
    TextView tvFace;
    TextView tvPrice;
    TextView tvOnlyOneMore;
    LinearLayout btnBuyNow;
    FrameLayout flProgressBar;

    public AsciiDetailFragment() {
    }

    public static AsciiDetailFragment newInstance() {
        AsciiDetailFragment fragment = new AsciiDetailFragment();

        return fragment;
    }

    public AsciiDetailContract.UserActionsListener getAsciiDetailPresenter() {
        return asciiDetailPresenter;
    }

    public void setAsciiDetailPresenter(AsciiDetailContract.UserActionsListener asciiDetailPresenter) {
        this.asciiDetailPresenter = asciiDetailPresenter;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ascii_detail, container, false);

        tvTags        = (TextView) view.findViewById(R.id.tvTags);
        tvFace        = (TextView) view.findViewById(R.id.tvFace);
        tvPrice       = (TextView) view.findViewById(R.id.tvPrice);
        tvOnlyOneMore = (TextView) view.findViewById(R.id.tvOnlyOneMore);
        btnBuyNow     = (LinearLayout) view.findViewById(R.id.btnBuyNow);
        flProgressBar = (FrameLayout) view.findViewById(R.id.flProgressBar);

        btnBuyNow.setOnClickListener(onClickBtnBuy());

        return view;
    }


    private View.OnClickListener onClickBtnBuy() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                asciiDetailPresenter.onClickBtnBuy();
            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ascii = (Ascii) getActivity().getIntent().getExtras().get(AppProperties.ASCII_KEY);
        showAscii(ascii);

        if(ascii.getStock() == 1)
        {
            showOnlyOneStockMsg();
        }
    }

    private void showOnlyOneStockMsg() {

        tvOnlyOneMore.setVisibility(View.VISIBLE);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == android.R.id.home)
        {
            asciiDetailPresenter.onClickBack();
        }

        return true;
    }


    @Override
    public void showAsciiListActivity() {

        getActivity().finish();

    }

    @Override
    public void showLoading() {

        flProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {

        flProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void showAscii(Ascii ascii) {

        tvTags.setText(ascii.getTagsTitle());
        tvFace.setText(ascii.getFace());
        tvPrice.setText(ascii.convertPriceToDollarText());
    }

    @Override
    public void showConfirmBuyMsg() {

        DialogUtils.showDialog(getActivity(),getString(R.string.buyQuestion),null, getString(R.string.yes), buyNow(),getString(R.string.no), dontBuy() );

    }



    private DialogInterface.OnClickListener dontBuy() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                asciiDetailPresenter.onClickBuyNo();
            }
        };
    }

    private DialogInterface.OnClickListener buyNow() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                asciiDetailPresenter.onClickYesBuy(ascii);
            }
        };
    }


    @Override
    public void showBuySuccessMsg() {

        DialogUtils.showSimpleDialog(getActivity(), null, getString(R.string.ascii_purchase_with_success), getString(R.string.ok), ok() );

    }

    private DialogInterface.OnClickListener ok() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                asciiDetailPresenter.onClickOk();
            }
        };
    }

    @Override
    public void showBuyFailureMsg() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
