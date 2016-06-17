package com.discount_ascii_warehouse.app.asciidetail;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;

/**
 * Created by mariobraga on 6/13/16.
 */
public interface AsciiDetailContract {

    interface View
    {
        void showAscii(Ascii ascii);
        void showConfirmBuyMsg();
        void showBuySuccessMsg();
        void showBuyFailureMsg();
        void showAsciiListActivity();
    }

    interface UserActionsListener
    {
        void onClickBtnBuy();
        void onClickYesBuy(Ascii ascii);
        void onClickOk();
        void onClickBuyNo();
        void onClickBack();

    }



}
