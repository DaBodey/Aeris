package com.samsung.android.example.helloaccessoryprovider.service;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Bodey on 7/15/14.
 */
public class WebAppInterface {

   public static Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
   public static final String getIcon(){
    return MyActivity.icona;
    }

}

