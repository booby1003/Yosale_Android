package com.yosale;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;
import com.yosale.communication.MyVolley;

/**
 * Created by EvilStorm on 2016-01-02.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {

        MyVolley.init(this);
        RequestQueue rq = DefaultRequestQueueFactory.create(this);
        rq.start();
        Volleyer.volleyer(rq).settings().setAsDefault().done();

    }
}
