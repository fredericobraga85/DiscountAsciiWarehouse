package com.discount_ascii_warehouse.app.data.asciirequest;

/**
 * Created by mariobraga on 6/16/16.
 */
public class FakeAsciiRequestRepositoryImpl implements AsciiRequestRepository {


    List<AsciiRequest> asciiRequestList = new ArrayList<>();

    @Override
    public void cacheRequest(AsciiRequest asciiRequest) {

            asciiRequestList.add(asciiRequest);
    }
}
