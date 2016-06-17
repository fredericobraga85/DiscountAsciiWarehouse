package com.discount_ascii_warehouse.app.data.asciirequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mariobraga on 6/16/16.
 */
public interface AsciiRequestRepository {

    long cacheRequest(AsciiRequest asciiRequest);

    AsciiRequest getCachedRequestInLastHour(AsciiRequest asciiRequest);

    void deleteCachedRequest(long id);

    void deleteAllCachedRequest();

    void deleteAllRequestsOlderThanOneHour();
}
