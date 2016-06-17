package com.discount_ascii_warehouse.app.asciilist;

import android.support.annotation.Nullable;

import com.discount_ascii_warehouse.app.data.ascii.Ascii;
import com.discount_ascii_warehouse.app.data.ascii.AsciiService;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;
import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequestRepository;
import com.discount_ascii_warehouse.app.utils.NDJSONParser;

import java.util.List;

/**
 * Created by mariobraga on 6/13/16.
 */
public class AsciiListPresenter implements AsciiListContract.UserActionsListener {

    private AsciiListContract.View asciiListView;
    private AsciiRequestRepository asciiRequestRepository;
    private AsciiService asciiService;

    // getters and setters
    public AsciiListContract.View getAsciiListView() {
        return asciiListView;
    }

    public void setAsciiListView(AsciiListContract.View asciiListView) {
        this.asciiListView = asciiListView;
    }

    public AsciiRequestRepository getAsciiRepository() {
        return asciiRequestRepository;
    }

    public void setAsciiRepository(AsciiRequestRepository asciiRequestRepository) {
        this.asciiRequestRepository = asciiRequestRepository;
    }

    public void setAsciiService(AsciiService asciiService) {
        this.asciiService = asciiService;
    }

    // User Action

    @Override
    public void onClickSearchAscii(final AsciiRequest asciiRequest) {

       asciiListView.showLoading();
       AsciiRequest cachedAsciiRequest =  asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        if(cachedAsciiRequest == null) {

            asciiService.getAsciiList(asciiRequest, new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList, String response) {

                    asciiRequest.setResponse(response);
                    asciiRequestRepository.cacheRequest(asciiRequest);

                    showList(asciiList);
                }

                @Override
                public void onError(String msg) {
                    showError(msg);
                }
            });
        }
        else
        {
            List<Ascii> asciiList = NDJSONParser.parse(cachedAsciiRequest.getResponse(), Ascii.class);
            showList(asciiList);
        }
    }

    @Override
    public void onRefreshAsciiList(final AsciiRequest asciiRequest) {

        asciiListView.showLoadingOnScroll();

        AsciiRequest cachedAsciiRequest = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        if(cachedAsciiRequest == null) {

            asciiService.getAsciiList(asciiRequest, new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList, String response) {

                    asciiRequest.setResponse(response);
                    asciiRequestRepository.cacheRequest(asciiRequest);

                    showRefreshedList(asciiList);
                }

                @Override
                public void onError(String msg) {
                    asciiListView.hideLoadingOnScroll();
                    asciiListView.showErrorLoadingScroll();
                }
            });
        }
        else
        {
            List<Ascii> asciiList = NDJSONParser.parse(cachedAsciiRequest.getResponse(),Ascii.class);
            showRefreshedList(asciiList);
        }
    }


    private void showRefreshedList(List<Ascii> asciiList) {
        if (asciiList.size() > 0) {
            asciiListView.showRefreshedAsciiList(asciiList);
        }

        asciiListView.hideLoadingOnScroll();

    }


    @Override
    public void onClickOnlyInStock(final AsciiRequest asciiRequest) {

        asciiListView.showLoading();

        if(asciiRequest.isOnlyInStock())
        {
            asciiListView.showOnlyStockIconActivated();
        }
        else
        {
            asciiListView.showOnlyStockIconDeactivated();
        }

        AsciiRequest cachedAsciiRequest = asciiRequestRepository.getCachedRequestInLastHour(asciiRequest);

        if(cachedAsciiRequest == null) {

            asciiService.getAsciiList(asciiRequest, new AsciiService.GetAsciiServiceCallBack() {
                @Override
                public void onAsciiLoaded(List<Ascii> asciiList, String response) {

                    asciiRequest.setResponse(response);
                    asciiRequestRepository.cacheRequest(asciiRequest);
                    showList(asciiList);
                }

                @Override
                public void onError(String msg) {
                    showError(msg);
                }
            });
        }
        else
        {
            List<Ascii> asciiList = NDJSONParser.parse(cachedAsciiRequest.getResponse(), Ascii.class);
            showList(asciiList);
        }
    }

    @Override
    public void onClickAscii(Ascii ascii) {

        asciiListView.showAsciiDetailActivity(ascii);
    }


    private void showError(String msg) {
        asciiListView.hideLoading();
        asciiListView.showErrorMsg(msg);
    }

    private void showList(List<Ascii> asciiList) {

        asciiListView.hideLoading();

        if (asciiList.size() > 0) {
            asciiListView.showSearchedAsciiList(asciiList);
        } else {
            asciiListView.showEmptyListMessage();
        }
    }



}
