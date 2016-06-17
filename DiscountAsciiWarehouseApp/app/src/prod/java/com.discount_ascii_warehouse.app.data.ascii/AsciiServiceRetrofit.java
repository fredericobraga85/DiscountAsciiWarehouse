package com.discount_ascii_warehouse.app.data.ascii;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;
import com.discount_ascii_warehouse.app.utils.AppProperties;
import com.discount_ascii_warehouse.app.utils.NDJSONParser;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mariobraga on 6/13/16.
 */
public class AsciiServiceRetrofit implements AsciiService {

    private final AsciiRetroFitService service;

    public AsciiServiceRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppProperties.URL_GET_ASCII_LIST)
                .build();

        service = retrofit.create(AsciiRetroFitService.class);

    }


    @Override
    public void getAsciiList(AsciiRequest asciiRequest, @NonNull final GetAsciiServiceCallBack callBack)   {

        Call<ResponseBody> result = service.getAsciiList(asciiRequest.getLimit(), asciiRequest.getSkip(),asciiRequest.getQuery(), asciiRequest.isOnlyInStock() ? 1 : 0);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                List<Ascii> asciiList = null;

                try {
                    String ndjson = response.body().string();
                    asciiList = NDJSONParser.parse(ndjson, Ascii.class);
                    callBack.onAsciiLoaded(asciiList, ndjson);
                }
                catch (IOException e) {
                    callBack.onError(e.getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                callBack.onError(t.getLocalizedMessage());

            }
        });
    }




    interface AsciiRetroFitService
    {
        @GET("/api/search")
        Call<ResponseBody> getAsciiList(@Query("limit") Integer limit,@Query("skip") Integer skip,@Query("q") String q,@Query("onlyInStock") Integer onlyInStock);

    }
}
