package com.discount_ascii_warehouse.app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by livetouch on 2/9/15.
 */
public class DialogUtils {

    public static void showDialog(Context context, String title, String msg, String textOk, DialogInterface.OnClickListener onClickOk, String textoCancel, DialogInterface.OnClickListener onClickCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title).setMessage(msg);
        builder.setPositiveButton(textOk, onClickOk);
        builder.setNegativeButton(textoCancel, onClickCancel);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showSimpleDialog(Context context, String title, String msg, String textOk, DialogInterface.OnClickListener onClickOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title).setMessage(msg);
        builder.setPositiveButton(textOk, onClickOk);

        AlertDialog dialog = builder.create();
        if(((Activity) context).isFinishing() == false) {
            dialog.show();
        }
    }

    public static void showDialogWithSearchList(Activity activity, String title, List<String> arrayString, int idLayout, int idList, int idEditText, DialogWithListListener listener) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(idLayout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        final ListView listView = (ListView) view.findViewById(idList);
        final SeacrhListAdapter searchListadapter = new SeacrhListAdapter(activity, android.R.layout.simple_list_item_1, android.R.id.text1, arrayString);
        listView.setAdapter(searchListadapter);
        listView.setOnItemClickListener(onItemClicked(listener, dialog , searchListadapter));

        EditText editText = (EditText) view.findViewById(idEditText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchListadapter.getPosArrayWithText(s.toString());
                searchListadapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.show();

    }



    private static ListView.OnItemClickListener onItemClicked(final DialogWithListListener listener, final AlertDialog dialog , final SeacrhListAdapter searchListAdapter) {
        return new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.cancel();
                listener.onItemClicked(searchListAdapter.getPosString(position));
            }
        };

    }


    public interface DialogWithListListener {
        public void onItemClicked(int position);


    }

    private static class SeacrhListAdapter extends ArrayAdapter<String> {

        Context context;
        int idLayout;
        int idTextView;
        static int size = 0;
        static List<String> data = null;
        static List<String> dataWithSearch = null;
        static List<Integer> listPosShowing = new ArrayList<Integer>();

        public SeacrhListAdapter(Context context, int idLayout, int idTextView, List<String> arString) {
            super(context, idLayout, arString);
            this.idLayout = idLayout;
            this.idTextView = idTextView;
            this.context = context;
            this.data = arString;
            getPosArrayWithText("");
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;

          // if(listPosShowing.contains(position)) {
               SearchListHolder holder = null;

//               if (row == null) {
                   LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                   row = inflater.inflate(idLayout, parent, false);

                   holder = new SearchListHolder();
                   holder.textView = (TextView) row.findViewById(idTextView);
                   holder.textView.setText(data.get(listPosShowing.get(position)));

                   row.setTag(holder);
//               } else {
//                   holder = (SearchListHolder) row.getTag();
//               }

               return row;
//           }
//            else
//           {
//               row.setVisibility(View.GONE);
//               return row;
//           }


        }

        class SearchListHolder {
            TextView textView;
        }


        static void getPosArrayWithText(String text)
        {
            listPosShowing.clear();
            int numPos = 0;
            size = 0;
            for(String t : data) {
                if (t.toLowerCase().contains(text.toLowerCase())) {
                    listPosShowing.add(numPos);
                    size++;
                }
                numPos++;
            }
        }

        static int getPosString(int pos)
        {
            return listPosShowing.get(pos);
        }



    }
}
