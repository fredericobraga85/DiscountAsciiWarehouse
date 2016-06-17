package com.discount_ascii_warehouse.app.data.ascii;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.discount_ascii_warehouse.app.data.asciirequest.AsciiRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mariobraga on 6/13/16.
 */
public interface AsciiService {

     interface GetAsciiServiceCallBack {

          void onAsciiLoaded(List<Ascii> asciiList, String response);
          void onError(String msg);
     }


     void getAsciiList(AsciiRequest asciiRequest, @NonNull GetAsciiServiceCallBack callBack);


}
