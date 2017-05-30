package com.bwei.haozi.moni0528;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bwei.haozi.moni0528.adapter.NewsAdapter;
import com.bwei.haozi.moni0528.bean.NewsBean;
import com.bwei.haozi.moni0528.utils.NetUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

public class MainActivity extends Activity {

    private ListView lv;
    private List<NewsBean.AppBean> list;
    private NewsAdapter adapter;
    private String path;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = 1;
        path = "http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=%E8%85%BE%E8%AE%AF%E8%BD%AF%E4%BB%B6&pageNo=" + page + "&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732";
        lv = (ListView) findViewById(R.id.lv);
        getData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private AlertDialog.Builder builder;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                builder = new AlertDialog.Builder(MainActivity.this);

                builder.setIcon(R.mipmap.ic_launcher);

                builder.setTitle("网络选择");

                builder.setPositiveButton("wifi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);

                        builder1.setIcon(R.mipmap.ic_launcher);

                        builder1.setTitle("检测到新版本，是否更新");

                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                getdataa();

                            }
                        });

                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder1.show();




                    }
                });

                builder.setNegativeButton("手机流量", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtil netUtil = new NetUtil();

                        netUtil.toSystemSetting(MainActivity.this);


                    }
                });

                builder.show();


            }
        });

    }

    private void getData() {
        RequestParams params = new RequestParams(path);

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result" + result.length());

                String json = result.substring(0, result.length() - 1);
                Gson gson = new Gson();

//                NewsBean newsBean = gson.fromJson(json, NewsBean.class);
//
//                List<NewsBean.AppBean> app = newsBean.getApp();

                list = gson.fromJson(json, NewsBean.class).getApp();
                adapter = new NewsAdapter(list, MainActivity.this);
                lv.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });


    }

    private void getdataa(){


        String url = "http://imtt.dd.qq.com/16891/E4E087B63E27B87175F4B9BC7CFC4997.apk?fsname=com.tencent.qlauncher_6.0.2_64170111.apk&csr=97c2";
        RequestParams params = new RequestParams(url);
        //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/myapp/");
        //自动为文件命名
        params.setAutoRename(true);

        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //apk下载完成后，调用系统的安装方法
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                startActivity(intent);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
            //网络请求之前回调
            @Override
            public void onWaiting() {
            }
            //网络请求开始的时候回调
            @Override
            public void onStarted() {
            }
            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Log.i("下载中","current："+ current +"，total："+total);
            }
        });

    }
}