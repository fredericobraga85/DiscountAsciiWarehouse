package com.discount_ascii_warehouse.app.asciilist;

import android.support.annotation.Nullable;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mariobraga on 6/13/16.
 */
public interface AsciiListContract {

    interface View
    {
        void showLoading();
        void hideLoading();
        void showSearchedAsciiList(List<Ascii> asciiList);
        void showRefreshedAsciiList(List<Ascii> asciiList);
        void showEmptyListMessage();
        void showErrorMsg(String msg);
        void showOnlyStockIconActivated();
        void showOnlyStockIconDeactivated();
        void showAsciiDetailActivity(Ascii ascii);
        void showLoadingOnScroll();
        void hideLoadingOnScroll();
        void showErrorLoadingScroll();

    }

    interface UserActionsListener
    {
        void onClickSearchAscii(AsciiRequest asciiRequest);
        void onRefreshAsciiList(AsciiRequest asciiRequest);
        void onClickOnlyInStock(AsciiRequest asciiRequest);
        void onClickAscii(Ascii ascii);
    }



}
