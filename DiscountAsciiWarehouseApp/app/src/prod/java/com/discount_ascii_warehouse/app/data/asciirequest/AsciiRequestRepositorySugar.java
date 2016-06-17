package com.discount_ascii_warehouse.app.data.asciirequest;

import java.util.Date;
import java.util.List;

/**
 * Created by mariobraga on 6/16/16.
 */
public class AsciiRequestRepositorySugar implements AsciiRequestRepository {


    @Override
    public long cacheRequest(AsciiRequest asciiRequest) {

        return asciiRequest.save();

    }

    @Override
    public AsciiRequest getCachedRequestInLastHour(AsciiRequest asciiRequest) {

        deleteAllRequestsOlderThanOneHour();

        String limit =   String.valueOf(asciiRequest.getLimit());
        String skip  =   String.valueOf(asciiRequest.getSkip());
        String query =   asciiRequest.getQuery();
        String onlyInStock = String.valueOf(asciiRequest.isOnlyInStock() ? 1 : 0);

        List<AsciiRequest> asciiRequestList = AsciiRequest.find(AsciiRequest.class,"LIMIT_DB = ? AND SKIP = ? AND QUERY = ? AND ONLY_IN_STOCK = ?", new String[]{limit , skip , query, onlyInStock}, null, "TIMESTAMP DESC", "1"  );

        if(asciiRequestList.size() == 0)
        {
            return null;
        }

        return asciiRequestList.get(0);
    }

    @Override
    public void deleteCachedRequest(long id) {

        AsciiRequest.deleteAll(AsciiRequest.class, "ID = ?" , String.valueOf(id));

    }

    @Override
    public void deleteAllCachedRequest() {

        AsciiRequest.deleteAll(AsciiRequest.class);
        AsciiRequest.executeQuery("delete from sqlite_sequence where name = ? ","ASCII_REQUEST");

    }

    @Override
    public void deleteAllRequestsOlderThanOneHour() {

        long actualTime = new Date().getTime();
        long hourBefore = actualTime - (1000 * 60 * 60);

        AsciiRequest.deleteAll(AsciiRequest.class, "TIMESTAMP < ?" , String.valueOf(hourBefore));

    }
}
