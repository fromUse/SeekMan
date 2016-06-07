package com.seekman.personal.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seekman.personal.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXEmojiObject;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by Administrator on 2016/5/28 0028.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, PlatformActionListener {

    //控件声明
    public ImageView register_return, wx_login, qq_login, xl_login, dialog_nickimage, dialog_passwdimage;
    private EditText register_username, register_code, dialog_nickname, dialog_passwd;
    private TextView register_next, register_yzcode, register_login;

    //Context对象
    Context context = RegisterActivity.this;

    //验证码倒计时
    private CountTimer countTimer;

    //短信验证事件监听
    private EventHandler eh;


    //显示密码
    private boolean topasswd = true;
    //昵称可用
    private boolean tousername = false;

    //进度对话框
    ProgressDialog progressDialog;

    //线程
    Thread thread;

    //微博事件
    //WeiboAuth mWeiboAuth;

    /**
     * 注册用户所需要的字段
     * user_username：注册的手机号（不可以重复注册）
     * user_passwd：登陆密码
     * user_nickname：昵称（唯一，不可重户）
     * <p/>
     * 重点说明：如果注册用户，此3个字段必须有，否则注册失败，且发送过来的字段要和上面的相符
     * <p/>
     * 注册用户的URL
     * GET请求：
     * http://172.30.84.10:8082/AndroidServer/registerservlet?user_username=13428216016&user_passwd=654&user_nickname=小名
     * <p/>
     * post请求：
     * http://172.30.84.10:8082/AndroidServer/registerservlet
     * <p/>
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        setContentView(R.layout.user_registered);

        init();
    }

    //销毁短信注册回调
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁ShareSDK
        ShareSDK.stopSDK();
        SMSSDK.unregisterEventHandler(eh);
    }

    private void init() {
        register_return = (ImageView) findViewById(R.id.register_return);
        wx_login = (ImageView) findViewById(R.id.wx_login);
        qq_login = (ImageView) findViewById(R.id.qq_login);
        xl_login = (ImageView) findViewById(R.id.xl_login);

        register_username = (EditText) findViewById(R.id.register_username);
        register_code = (EditText) findViewById(R.id.register_code);

        register_next = (TextView) findViewById(R.id.register_next);
        register_yzcode = (TextView) findViewById(R.id.register_yzcode);
        register_login = (TextView) findViewById(R.id.register_login);

        //初始化ShareSDK
        ShareSDK.initSDK(context);

        //每六十秒可执行一次，每秒更新一次
        countTimer = new CountTimer(60000, 1000);

        register_return.setOnClickListener(this);
        wx_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        xl_login.setOnClickListener(this);
        register_next.setOnClickListener(this);
        register_yzcode.setOnClickListener(this);
        register_login.setOnClickListener(this);

    }

    //控件事件监听
    @Override
    public void onClick(View v) {
        int i = v.getId();
        String username = register_username.getText().toString();
        String code = register_code.getText().toString();
        if (i == R.id.register_return) {
            //返回到登录页面
            intentLogin();
        } else if (i == R.id.wx_login) {
            loginByWX();
            Toast.makeText(context, "wx_login", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.qq_login) {
            loginByQQ();
            Toast.makeText(context, "qq_login", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.xl_login) {
            loginByXL();
            Toast.makeText(context, "xl_login", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.register_next) {
            if (sendCode(code)) {
                //验证短信验证码
                SMSSDK.submitVerificationCode("86", username, code);
                //调用进度对话框
                sendProgressDialog();
                //注册对话框
                sendDialog();
            }

        } else if (i == R.id.register_yzcode) {
            if (sendUsername(username)) {
                //开启倒计时
                countTimer.start();
                //发送验证码
                sendSMSRandom();
            }
        } else if (i == R.id.register_login) {
            //返回到登录页面
            intentLogin();
        }
    }

    //登录成功调用
    private void loginSuccess(String username, String userimage) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("username", username)
                .putExtra("userimage", userimage);
//        setResult(SearchViewCompatIcs.MySearchView);
        finish();
    }


    //微信第三方登录
    private void loginByWX() {
        //1.得到微信的登录平台
        Platform platform = ShareSDK.getPlatform(context, Wechat.NAME);
        //2.增加监听事件
        platform.setPlatformActionListener(this);
        //3.判断授权是否已经验证（是否正常登录）
        if (platform.isValid()) {
            //获取第三方平台显示的信息
            String username = platform.getDb().getUserName();
            String passwd = platform.getDb().getUserId();
            String userimage = platform.getDb().getUserIcon();
            Log.d("loginByWX", "第三方平台的信息:名称" + username + "UID" + passwd + "图片" + userimage);
            loginSuccess(username, userimage);
        } else {
            //如果没有授权登录
            platform.showUser(null);
        }
    }

    //qq第三方登录
    private void loginByQQ() {
        //1.得到QQ的登录平台
        Platform platform = ShareSDK.getPlatform(context, QQ.NAME);
        //2.增加监听事件
        platform.setPlatformActionListener(this);
        //3.判断授权是否已经验证（是否正常登录）
        if (platform.isValid()) {
            //获取第三方平台显示的信息
            String username = platform.getDb().getUserName();
            String passwd = platform.getDb().getUserId();
            String userimage = platform.getDb().getUserIcon();
            Log.d("loginByQQ", "第三方平台的信息:名称" + username + "UID" + passwd + "图片" + userimage);
            loginSuccess(username, userimage);
        } else {
            //如果没有授权登录
            platform.showUser(null);
        }
    }

    //新浪第三方登录
    private void loginByXL() {
        //1.得到新浪的登录平台
        Platform platform = ShareSDK.getPlatform(context, SinaWeibo.NAME);
        //2.增加监听事件
        platform.setPlatformActionListener(this);
        //3.判断授权是否已经验证（是否正常登录）
        if (platform.isValid()) {
            //获取第三方平台显示的信息
            String username = platform.getDb().getUserName();
            String passwd = platform.getDb().getUserId();
            String userimage = platform.getDb().getUserIcon();
            Log.d("loginByXL", "第三方平台的信息:名称" + username + "UID" + passwd + "图片" + userimage);
            loginSuccess(username, userimage);
        } else {
            //如果没有授权登录
            platform.showUser(null);
        }
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //获取第三方平台显示的信息
        String username = platform.getDb().getUserName();
        String passwd = platform.getDb().getUserId();
        String userimage = platform.getDb().getUserIcon();
        Log.d("onComplete", "第三方平台的信息:名称" + username + "UID" + passwd + "图片" + userimage);
        //返回登录页面
        loginSuccess(username, userimage);
    }

    //授权失败
    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(context, platform.getName() + "授权失败,请稍后重试", Toast.LENGTH_SHORT).show();
    }

    //授权取消
    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(context, platform.getName() + "授权已取消", Toast.LENGTH_SHORT).show();
    }


    //Dialog对话框
    public void sendDialog() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.registered_dialog, null);
        dialog_nickname = (EditText) dialogView.findViewById(R.id.dialog_nickname);
        dialog_passwd = (EditText) dialogView.findViewById(R.id.dialog_passwd);
        dialog_nickimage = (ImageView) dialogView.findViewById(R.id.dialog_nickimage);
        dialog_passwdimage = (ImageView) dialogView.findViewById(R.id.dialog_passwdimage);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.logo)
                .setTitle("注册")
                .setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendRegisterPD();
                        Log.d("send------------","iiiiiiiiiii");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "取消注册", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();

        dialog_nickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifUserNickname();
            }
        });
        dialog_passwdimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topasswd) {
                    dialog_passwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    topasswd = false;
                    Toast.makeText(context, "显示密码", Toast.LENGTH_SHORT).show();
                } else {
                    dialog_passwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Toast.makeText(context, "隐藏密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void ifUserNickname() {
        if (tousername) {
            new Thread() {
                @Override
                public void run() {
                    String user_nickname = dialog_nickname.getText().toString();
                    String url = final_data.IFUSERNICKNAME;
                    try {
                        URL HttpUrl = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) HttpUrl.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setReadTimeout(5000);
                        OutputStream outputStream = connection.getOutputStream();
                        String content = "user_nickname=" + user_nickname;
                        outputStream.write(content.getBytes());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String string;
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((string = reader.readLine()) != null) {
                            stringBuffer.append(string);
                        }
                        Log.d("dialog_nickname------------", stringBuffer.toString());
                        if (stringBuffer.toString().equals("true")) {
                            Toast.makeText(RegisterActivity.this, "昵称可用", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "昵称不可用", Toast.LENGTH_SHORT).show();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            tousername = true;
        }
    }

    private void sendRegisterPD() {
        progressDialog = ProgressDialog.show(context, "", "正在注册", true, false);

        final String username = register_username.getText().toString();
        final String passwd = dialog_passwd.getText().toString();
        final String nickname = dialog_nickname.getText().toString();

        if (tousername) {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        String url = final_data.REGISTERED;

                        URL HttpUrl = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) HttpUrl.openConnection();
                        connection.setRequestMethod("POST");//请求方式post
                        connection.setReadTimeout(5000);//请求超时时间
                        OutputStream outputStream = connection.getOutputStream();
                        String content = "user_username=" + username +
                                "&user_passwd=" + passwd +
                                "&user_nickname=" + nickname;
                        outputStream.write(content.getBytes());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String string;
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((string = reader.readLine()) != null) {
                            stringBuffer.append(string);
                        }
                        Log.d("StringBufferPOST ", stringBuffer.toString());

                        Log.d("str------------------", stringBuffer.toString());
                        if (stringBuffer.toString().equals("true")) {
                            Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                            //跳转到登录页面
                            intentLogin();
                            progressDialog.cancel();
                        } else {
                            Toast.makeText(context, "注册失败，请检查用户名及昵称", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDialog.cancel();//关闭进度对话框
                }
            };
            thread.start();
        } else {
            ifUserNickname();
            progressDialog.cancel();
        }
    }

    //注册

    //ProgressDialog进度对话框
    public void sendProgressDialog() {
        progressDialog = ProgressDialog.show(context, "", "正在验证", true, false);
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                progressDialog.dismiss();//关闭进度对话框
            }
        };
        thread.start();
    }

    //跳转到登录页面
    public void intentLogin() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }

    //判断验证码
    public boolean sendCode(String code) {
        if (code == null || code.isEmpty()) {
            Toast.makeText(context, "验证码不能为空", Toast.LENGTH_SHORT).show();
            Log.d("验证码不能为空", "验证码不能为空" + code.length());
            return false;
        } else if (code.length() != 4) {
            Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
            Log.d("验证码错误", "验证码错误" + code.length());
            return false;
        } else {
            return true;
        }
    }

    //判断手机号码
    public boolean sendUsername(String username) {
        if (username == null || username.isEmpty()) {
            Toast.makeText(context, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            Log.d("手机号码不能为空", "手机号码不能为空" + username.length());
            return false;
        } else if (username.length() != 11) {
            Toast.makeText(context, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            Log.d("手机号码格式不正确", "手机号码格式不正确" + username.length());
            return false;
        } else {
            return true;
        }
    }

    //发送短信验证码
    public void sendSMSRandom() {
        //初始化短信验证
        SMSSDK.initSDK(this, final_data.APPKEY, final_data.APPSECRET);
        //短信验证事件监听
        eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        /*Toast.makeText(context, "验证码验证成功", Toast.LENGTH_SHORT).show();*/
                        Log.d("验证码验证成功", "验证码验证成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        /*Toast.makeText(context, "验证码获取成功", Toast.LENGTH_SHORT).show();*/
                        Log.d("验证码获取成功", "验证码获取成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    /*Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();*/
                    Log.d("sendSMSRandom验证码错误", "sendSMSRandom验证码错误");
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

        //将短信验证码发送到服务器
        String username = register_username.getText().toString();
        SMSSDK.getVerificationCode("86", username.toString());
    }


    private void weiboAuth() {
//        mWeiboAuth = new  WeiboAuth()
    }


    //每隔一分钟可点击发送一次验证码
    public class CountTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         *                          时间间隔是多久
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         *                          回调onTick方法，每隔多久执行一次
         */
        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //间隔时间内调用
        @Override
        public void onTick(long millisUntilFinished) {
            //更新页面组件
            register_yzcode.setText(millisUntilFinished / 1000 + "秒后发送");
            register_yzcode.setClickable(false);
        }

        //间隔时间结束时候调用
        @Override
        public void onFinish() {
            //更新页面组件
            register_yzcode.setText("发送验证码");
            register_yzcode.setClickable(true);
        }
    }
}
