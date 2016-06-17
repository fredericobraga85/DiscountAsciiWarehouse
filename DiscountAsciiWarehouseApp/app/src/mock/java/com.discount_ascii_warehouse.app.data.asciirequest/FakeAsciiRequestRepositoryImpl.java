package com.discount_ascii_warehouse.app.data.asciirequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariobraga on 6/16/16.
 */
public class FakeAsciiRequestRepositoryImpl implements AsciiRequestRepository {


    List<AsciiRequest> asciiRequestList = new ArrayList<>();


    @Override
    public long cacheRequest(AsciiRequest asciiRequest) {

        return 0;
    }

    @Override
    public AsciiRequest getCachedRequestInLastHour(AsciiRequest asciiRequest) {


        return null;
    }

    @Override
    public void deleteCachedRequest(long id) {

    }

    @Override
    public void deleteAllCachedRequest() {



    }

    @Override
    public void deleteAllRequestsOlderThanOneHour() {

    }
}
