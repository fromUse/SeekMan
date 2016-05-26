package com.seekman.library.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chen-gui on 16-4-29.
 * <p/>
 * 此类负责异步加载json
 *
 *
 *
 * 当请求方式时GET 调用execute()不用家参数
 *
 * <p/>
 *
 * 当请求方式时POST 调用execute()参数是post参数
 */
public abstract class StringLoad extends AsyncTask<String, Void, String> {
    private static final String TAG = "StringLoad";
    //GET和POST请求标记
    public static final int METHOD_POST = 0;
    public static final int METHOD_GET = 1;

    private StringBuffer sb = null;
    private String line = null;
    private BufferedReader bufferedReader = null;

    BufferedReader input = null;
    PrintWriter out = null;

    private int method = 1;

    /**
     *
     * @param method 请求url
     */
    public StringLoad(int method) {
        this.method = method;

    }


    @Override
    protected String doInBackground(String... params) {

        //参数一时url地址
        String url = params[0];
        //参数二是post的参数
        String post_param = null;

        if (params.length > 1) {
            post_param = params[1];
        }


        if (method == StringLoad.METHOD_GET) {
            return doGet (url);
        } else {
            return doPost (url, post_param);
        }

    }

    /**
     * @param url get请求地址
     *
     * @return 返回请求json结果
     */
    protected String doGet(String url) {
        try {
            URL httpURL = new URL (url);
            sb = new StringBuffer ();
            HttpURLConnection con = (HttpURLConnection) httpURL.openConnection ();
            if (con != null) {

                con.setRequestMethod ("GET");
                con.setConnectTimeout (5000);
                if (con.getResponseCode () == 200) {

                    input = new BufferedReader (new InputStreamReader (con.getInputStream (), "utf-8"));
                    if (input != null) {
                        while ((line = input.readLine ()) != null) {
                            sb.append (line);
                        }
                        return sb.toString ();
                    }
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return null;

    }

    /**
     * @param url        请求地址
     * @param post_param post的请求参数
     *
     * @return 返回请求json结果
     */
    protected String doPost(String url, String post_param) {

        try {
            URL httpURL = new URL (url);
            sb = new StringBuffer ();
            HttpURLConnection con = (HttpURLConnection) httpURL.openConnection ();
            Thread.sleep (1500);
            if (con != null) {

                con.setRequestMethod ("POST");
                con.setConnectTimeout (5000);
                con.setDoOutput (true);
                con.setDoInput (true);

                out = new PrintWriter (con.getOutputStream ());
                if (post_param != null) {
                    out.print (post_param);
                    out.flush ();
                }

                input = new BufferedReader (new InputStreamReader (con.getInputStream (), "utf-8"));
                if (input != null) {
                    while ((line = input.readLine ()) != null) {
                        sb.append (line);
                    }
                    return sb.toString ();
                }

            }


        } catch (MalformedURLException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } finally {
            if (out != null) {

                out.close ();

            }
        }

        return null;
    }


    //网络数据获取后在主线程执行相关数据解析好ui更新
    @Override
    protected void onPostExecute(String result) {
        executeUI (result);
    }

    /**
     * 耗时操作之后，更新UI的所有逻辑写在这个方法中
     *
     * @param result 参数是json数据字符串形式，
     *               <p/>
     *               json数据需要解析成项目实际需求的形式
     */
    abstract public void executeUI(String result);
}
