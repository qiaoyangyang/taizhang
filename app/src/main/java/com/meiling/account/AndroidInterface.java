package com.meiling.account;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.just.agentwebX5.AgentWeb;


public class AndroidInterface {


    private AgentWeb agent;
    private Activity context;

    public AndroidInterface(AgentWeb agent, Activity context) {

        this.agent = agent;
        this.context = context;

    }

    @JavascriptInterface

    public void payCallAndroid(String methodname) {
        Log.d("", "payCallAndroid: "+methodname);
        context.finish();
    }
}
