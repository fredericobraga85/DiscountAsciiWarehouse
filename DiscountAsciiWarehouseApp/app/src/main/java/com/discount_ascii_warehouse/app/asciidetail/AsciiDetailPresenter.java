package com.discount_ascii_warehouse.app.asciidetail;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;

/**
 * Created by mariobraga on 6/13/16.
 */
public class AsciiDetailPresenter implements AsciiDetailContract.UserActionsListener {

    private AsciiDetailContract.View asciiDetailView;


    public AsciiDetailContract.View getAsciiDetailView() {
        return asciiDetailView;
    }

    public void setAsciiDetailView(AsciiDetailContract.View asciiDetailView) {
        this.asciiDetailView = asciiDetailView;
    }



    @Override
    public void onClickOk() {

        asciiDetailView.showAsciiListActivity();

    }

    @Override
    public void onClickBtnBuy() {

        asciiDetailView.showConfirmBuyMsg();
    }

    @Override
    public void onClickYesBuy(Ascii ascii) {

        asciiDetailView.showBuySuccessMsg();
    }

    @Override
    public void onClickBuyNo() {

        asciiDetailView.showBuyFailureMsg();
    }

    @Override
    public void onClickBack() {

        asciiDetailView.showAsciiListActivity();
    }



}
