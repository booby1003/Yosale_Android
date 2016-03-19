package com.yosale.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.yosale.R;
import com.yosale.communication.ConstantURL;
import com.yosale.utils.LogUtil;

import java.io.File;


/**
 * Created by Administrator on 2016-03-14.
 */
public class CommTestFragment extends Fragment {

    private TextView tv_result;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View baseView = inflater.inflate(R.layout.comm_test_activity, container, false);
        tv_result = (TextView)baseView.findViewById(R.id.tv_result);
        tv_result.setMovementMethod(new ScrollingMovementMethod());

        baseView.findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTest();
            }
        });

        baseView.findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTest();
            }
        });


        return baseView;
    }

    private void getTest(){
//        http://evilstorm.godohosting.com/yosale/api/Contents?saleType=all&startIndex=0&pageIndex=1
        Uri.Builder builder = Uri.parse(ConstantURL.getUrl("api/Contents/getContents")).buildUpon();
        builder.appendQueryParameter("saleType", "all");
        builder.appendQueryParameter("startIndex", "0");
        builder.appendQueryParameter("pageIndex", "1");
        Volleyer.volleyer()
                .get(builder.toString())
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv_result.setText("Get Response : " + response);

                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv_result.setText("Get Error Response : " + error.toString());
                    }
                })
                .execute()
        ;
    }



    private void postTest(){
        String filePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"park_2.jpg";
        LogUtil.e("1111 filePath: " + filePath);

        Volleyer.volleyer()
                .post(ConstantURL.getUrl("api/Account/updateAccountInfo"))
                .addStringPart("u_index", "4")
                .addStringPart("email", "messi@gmail.com")
                .addStringPart("nickname", "park_2")
                .addStringPart("picture", "park_2.jpg")
                .addStringPart("alarm", "1")
                .addStringPart("seller", "0")
                .addFilePart("files[]", new File(filePath))
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv_result.setText("Post Response : " + response);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.e("1111  :  " + new String(error.networkResponse.data));
                        tv_result.setText(new String(error.networkResponse.data));
                    }
                })
                .execute();
    }
}
