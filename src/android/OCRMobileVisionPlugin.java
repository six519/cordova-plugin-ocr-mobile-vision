package com.ferdinandsilva.android;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.ContentValues;
import android.net.Uri;
import android.app.Activity;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OCRMobileVisionPlugin extends CordovaPlugin {
    public static final String TAG = "OCRMobileVisionPlugin";
    public static Context ctx;
    private CordovaInterface thisCordovaInterface;
    private CallbackContext thisCallbackContext;

    public OCRMobileVisionPlugin() {
    }

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        OCRMobileVisionPlugin.ctx = cordova.getActivity().getApplicationContext();
        thisCordovaInterface = cordova;
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        thisCallbackContext = callbackContext;

        if ("recognize".equals(action)) {
            Intent intent = new Intent(OCRMobileVisionPlugin.ctx, OCRActivity.class);
            thisCordovaInterface.setActivityResultCallback(this);
            thisCordovaInterface.getActivity().startActivityForResult(intent, 600);
        } else {
            return false;
        }

        return true;    
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 600) {
            if(resultCode == Activity.RESULT_OK) {
                String outStr = data.getStringExtra("text");
                thisCallbackContext.success(outStr);
            }
        }
    }


}