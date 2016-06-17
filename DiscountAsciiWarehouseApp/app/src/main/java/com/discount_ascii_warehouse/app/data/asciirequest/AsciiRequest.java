package com.discount_ascii_warehouse.app.data.asciirequest;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.Date;

/**
 * Created by mariobraga on 6/16/16.
 */

public class AsciiRequest extends SugarRecord  {

    public AsciiRequest(){}

    public AsciiRequest(int limit, int skip, String query , boolean isOnlyInStock)
    {
        setTimestamp(new Date().getTime());
        setLimit(limit);
        setSkip(skip);
        setQuery(query);
        setOnlyInStock(isOnlyInStock);
    }

    private long id;
    private long timestamp;

    @Column(name = "LIMIT_DB")
    private int limit;
    private int skip;
    private String query;
    private boolean onlyInStock;
    private String response;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isOnlyInStock() {
        return onlyInStock;
    }

    public void setOnlyInStock(boolean onlyInStock) {
        this.onlyInStock = onlyInStock;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }



}
