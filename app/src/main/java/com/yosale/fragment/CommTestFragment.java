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
        baseView.findViewById(R.id.btn_content_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postContentTest();
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
                .addStringPart("nickname", "messi")
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

    private void postContentTest(){
        String filePath1 = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"park_5.jpg";
        String filePath2 = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"park_6.png";
        String filePath3 = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"park_7.jpg";

        LogUtil.e("1111 File Path : " + filePath1);
        File file = new File(filePath1);
        if(file.exists()){
            LogUtil.e("1111 File EXIST");
        }else{
            LogUtil.e("1111 File Not EXIST");
        }

//        'u_index' => $u_index,
//                's_index' => $s_index,
//                'title' => $title,
//                'content' => $content,
//                'sdate' => $sdate,
//                'edate' => $edate,
//                'sale_type' => $sale_type,
//                'sale_rate' => $sale_rate,
//                'show_content_info' => $show_content_info,
//                'store_name' => $store_name,
//                'local_lon' => $local_lon,
//                'local_lat' => $local_lat,

        Volleyer.volleyer()
                .post(ConstantURL.getUrl("api/Contents/postContent"))
                .addStringPart("u_index", "1")
                .addStringPart("s_index", "1")
                .addStringPart("title", "등록 테스트 입니다. ")
                .addStringPart("content", "등록 테스트!! ")
                .addStringPart("sdate", "2016-03-28 00:00:00")
                .addStringPart("edate", "2016-03-31 23:59:59")
                .addStringPart("sale_type", "1")
                .addStringPart("sale_rate", "30")
                .addStringPart("show_content_info", "1")
                .addStringPart("store_name", "0")
                .addStringPart("local_lon", "1")
                .addStringPart("local_lat", "0")
                .addFilePart("files[]", new File(filePath1))
                .addFilePart("files[]", new File(filePath2))
                .addFilePart("files[]", new File(filePath3))

                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv_result.setText("Post Response : " + response);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            LogUtil.e("1111  :  " + new String(error.networkResponse.data));
                            tv_result.setText(new String(error.networkResponse.data));
                        } else {
                            tv_result.setText(new String(error.toString()));
                        }

                    }
                })
                .execute();
    }

}
