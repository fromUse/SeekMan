package com.seekman.personal.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seekman.library.utils.ImageLoad;
import com.seekman.library.utils.StringLoad;
import com.seekman.personal.R;

import java.util.Map;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView information_finish, information_username;
    private ImageView information_retrurn, information_image;
    private EditText
            information_nickname,
            information_name,
            information_age,
            information_sex,
            information_city,
            information_passwd,
            passwd_edit;
    private String username, passwd, nickname;

    //修改用户信息字段名
    private String
            user_username,
            user_passwd,
            user_nickname,
            user_name,
            user_sex,
            user_age,
            city_name;
    //修改信息服务器返回的值
    private String ifmt;
    private boolean topasswd = false;
    private boolean ifpasswd = false;


    private Handler handler;
    //json数据存储
    Map<String, String> map = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        requestWindowFeature (Window.FEATURE_NO_TITLE);//去掉标题
        setContentView (R.layout.user_information);
        init ();


    }

    private void init() {
        information_retrurn = (ImageView) findViewById (R.id.information_return);
        information_finish = (TextView) findViewById (R.id.information_finish);
        information_username = (TextView) findViewById (R.id.information_username);
        information_image = (ImageView) findViewById (R.id.information_image);
        information_nickname = (EditText) findViewById (R.id.information_nickname);
        information_name = (EditText) findViewById (R.id.information_name);
        information_age = (EditText) findViewById (R.id.information_age);
        information_sex = (EditText) findViewById (R.id.information_sex);
        information_city = (EditText) findViewById (R.id.information_city);
        information_passwd = (EditText) findViewById (R.id.information_passwd);

        //从本地取出
        SharedPreferences sharedPreferences = getSharedPreferences ("userfile", Activity.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String information = sharedPreferences.getString ("information", "false");
            username = sharedPreferences.getString ("username", "");
            if (information.equals ("false")) {
                //从服务器获取到当前用户的信息
                HandlerThread handlerThread = new HandlerThread ("Network");
                handlerThread.start ();
                Handler handler = new Handler (handlerThread.getLooper ());
                handler.postDelayed (new TimerTask () {
                    @Override
                    public void run() {
                        HttpSetinformation (username);
                    }
                }, 1000);
            } else {


                passwd = sharedPreferences.getString ("passwd", "");
                user_nickname = sharedPreferences.getString ("user_nickname", "");
                user_sex = sharedPreferences.getString ("user_sex", "");
                user_age = sharedPreferences.getString ("user_age", "");
                city_name = sharedPreferences.getString ("city_name", "");

                information_username.setText (username);

                information_nickname.setText (user_nickname);
                information_name.setText (user_name);
                information_age.setText (user_age);
                information_sex.setText (user_sex);
                information_city.setText (city_name);

            }


        }


        information_retrurn.setOnClickListener (this);
        information_finish.setOnClickListener (this);
        information_image.setOnClickListener (this);
        information_nickname.setOnClickListener (this);
        information_name.setOnClickListener (this);
        information_age.setOnClickListener (this);
        information_sex.setOnClickListener (this);
        information_city.setOnClickListener (this);
        information_passwd.setOnClickListener (this);

    }

    //从服务器获取到当前用户的信息
    private void HttpSetinformation(final String username) {

        new StringLoad (StringLoad.METHOD_GET) {

            @Override
            public void executeUI(final String result) {
                Data (result);
            }


        }.execute (final_data.SHAREDINFORMATION + "?user_username=" + username);
    }

    private void Data(String res) {
        Gson gson = new Gson ();
        Person rson = gson.fromJson (res, Person.class);


        information_image.setTag (rson.getUrl ());

        new ImageLoad (information_image).execute (rson.getUrl ());


        if (rson.getUser_Name () == null || rson.getUser_Name ().equals ("null")) {
            information_nickname.setText ("");
        } else {
            information_nickname.setText (rson.getUser_Name ());
        }

        if (rson.getUser_Name () == null || rson.getUser_Name ().equals ("null")) {
            information_name.setText ("");
        } else {
            information_name.setText (rson.getUser_Name ());
        }
        if (rson.getUser_Age () == "0" || rson.equals ("0")) {
            information_age.setText ("");
        } else {
            information_age.setText (rson.getUser_Age ());
        }
        if (rson.getUser_Sex () == null || rson.getUser_Sex ().equals ("null")) {
            information_sex.setText ("");
        } else {
            information_sex.setText (rson.getUser_Sex ());
        }
        if (rson.getCity_Name () == null || rson.getCity_Name ().equals ("null")) {
            information_city.setText ("");
        } else {
            information_city.setText (rson.getCity_Name ());
        }

        //String imagePath = information_image.getTag ().toString ();

        String userName = rson.getUser_Name ();
        String userNickname = rson.getUser_Nickname ();
        String Name = rson.getUser_Name ();
        String Sex = rson.getUser_Sex ();
        String Age = rson.getUser_Age ();
        String City_name = rson.getCity_Name ();
//保存到本地
        SharedPreferences sharedPreferences = getSharedPreferences ("userfile", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString ("username", userName)
                .putString ("user_nickname", userNickname)
                .putString ("user_name", Name)
                .putString ("user_sex", Sex)
                .putString ("user_age", Age)
                .putString ("city_name", City_name)
                .putString ("passwd", passwd)
                .putString ("iflogin", "true")
                .putString ("information", "true");
        editor.commit ();

    }

       /* new Thread() {

            @Override
            public void run() {

                String url = final_data.SHAREDINFORMATION;
                try {
                    URL Httpurl = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) Httpurl.openConnection();
                    con.setRequestMethod("POST");
                    con.setReadTimeout(5000);
                    OutputStream out = con.getOutputStream();
                    String content = "user_username=" + username;
                    out.write(content.getBytes());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String string;
                    StringBuffer sb = new StringBuffer();
                    while ((string = reader.readLine()) != null) {
                        sb.append(string);
                    }
                    Log.d("sharedInformation", sb.toString());

                    parseJson(sb.toString(), username);

                    String imagePath;
                    String userName;
                    String userNickname;
                    String Name;
                    String Sex;
                    String Age;
                    String City_name;


                    //保存到本地
                    SharedPreferences sharedPreferences = getSharedPreferences("userfile", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //将map数据遍历出来显示到UI界面

                    imagePath = map.get("user_Images");
                    new HttpImage(imagePath, handler, information_image);
                    editor.putString("user_image",imagePath);

                    userName = map.get("user_Username");
                    information_username.setText(userName);
                    editor.putString("username",userName);

                    userNickname = map.get("user_Nickname");
                    information_nickname.setText(userNickname);
                    editor.putString("user_nickname",userNickname);

                    Name = map.get("user_Name");
                    information_name.setHint(Name);
                    editor.putString("user_name",Name);

                    Sex = map.get("user_Sex");
                    information_sex.setHint(Sex);
                    editor.putString("user_sex",Sex);

                    Age = map.get("user_Age");
                    information_age.setHint(Age);
                    editor.putString("user_age",Age);

                    City_name = map.get("city_Name");
                    information_city.setHint(City_name);
                    editor.putString("city_name",City_name);

                    //存入一个值标记是否通过网络获取信息了
                    editor.putString("information","true");
                    editor.putString("iflogin","true");
                    editor.putString("passwd",passwd);
                    editor.commit();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    //解析服务器发送过来的Json数据
    private Map<String, String> parseJson(String json, String username) {
        try {
            //读取JSON对象
            JSONObject object = new JSONObject(json);

            String user_Username = object.getString("user_Username");

            if (user_Username == username) {
                String user_Passwd = object.getString("user_Passwd");
                String user_Name = object.getString("user_Name");
                String user_Nickname = object.getString("user_Nickname");
                String user_Sex = object.getString("user_Sex");
                String user_Age = object.getString("user_Age");
                String user_Images = object.getString("user_Images");
                String city_Name = object.getString("city_Name");

                map = new HashMap<String, String>();
                map.put("user_Username", user_Username);
                map.put("user_Passwd", user_Passwd);
                map.put("user_Name", user_Name);
                map.put("user_Nickname", user_Nickname);
                map.put("user_Sex", user_Sex);
                map.put("user_Age", user_Age);
                map.put("user_Images", user_Images);
                map.put("city_Name", city_Name);

                Log.d("parseJson", map.toString());
                return map;
            } else {
                Toast.makeText(InformationActivity.this, "获取不到数据", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;*/


    //完成信息修改发送到服务器
    private void informationText() {
        final ProgressDialog progressDialog = new ProgressDialog (this);
        progressDialog.setProgressStyle (ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle ("请稍等");
        progressDialog.setMessage ("玩命修改中...");
        progressDialog.setIcon (R.mipmap.logo);
        progressDialog.setIndeterminate (true);
        progressDialog.setCancelable (true);
        progressDialog.show ();


        //从界面获取修改的信息内容
        user_username = information_username.getText ().toString ();
        user_passwd = information_passwd.getText ().toString ();
        user_nickname = information_nickname.getText ().toString ();
        user_name = information_name.getText ().toString ();
        user_sex = information_sex.getText ().toString ();
        user_age = information_age.getText ().toString ();
        city_name = information_city.getText ().toString ();
        if (!ifpasswd) {
            user_passwd = "";
        }
        if (user_username == null || user_username.equals ("")) {
            user_username = "0";
        }
        if (user_passwd == null || user_passwd.equals ("")) {
            user_passwd = "0";
        }
        if (user_nickname == null || user_nickname.equals ("")) {
            user_nickname = "0";
        }
        if (user_name == null || user_name.equals ("")) {
            user_name = "0";
        }
        if (user_sex == null || user_sex.equals ("")) {
            user_sex = "0";
        }
        if (user_age == null || user_age.equals ("")) {
            user_age = "0";
        }
        if (city_name == null || city_name.equals ("")) {
            city_name = "0";
        }

        new StringLoad (StringLoad.METHOD_GET) {

            @Override
            public void executeUI(final String result) {
                if (result.equals ("true")) {

                    //将信息存到本地
                    SharedPreferences shared = getSharedPreferences ("userfile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit ();
                    editor.putString ("user_username", user_username)
                            .putString ("user_passwd", user_passwd)
                            .putString ("user_nickname", user_nickname)
                            .putString ("user_name", user_name)
                            .putString ("user_sex", user_sex)
                            .putString ("user_age", user_age)
                            .putString ("city_name", city_name)
                            .putString ("iflogin", "true")
                            .putString ("information", "true");

                    Toast.makeText (InformationActivity.this, "保存成功", Toast.LENGTH_SHORT).show ();

                    progressDialog.cancel ();

                    Intent intent = new Intent ();
                    setResult (CONTEXT_INCLUDE_CODE, intent.putExtra ("username", user_username)
                            .putExtra ("iflogin", "true"));
                    finish ();

                } else if (result.equals ("false")) {
                    progressDialog.cancel ();
                    Toast.makeText (InformationActivity.this, "嗷呜！居然失败了！", Toast.LENGTH_SHORT).show ();
                }
            }


        }.execute (final_data.INFORMATION + "?user_username=" + user_username +
                "&user_passwd=" + user_passwd +
                "&user_nickname=" + user_nickname +
                "&user_name=" + user_name +
                "&user_sex=" + user_sex +
                "&user_age=" + user_age +
                "&city_name=" + city_name);
    }

       /* new Thread() {
            @Override
            public void run() {
                //从界面获取修改的信息内容
                user_username = information_username.getText().toString();
                user_passwd = information_passwd.getText().toString();
                user_nickname = information_nickname.getText().toString();
                user_name = information_name.getText().toString();
                user_sex = information_sex.getText().toString();
                user_age = information_age.getText().toString();
                city_name = information_city.getText().toString();
                if (!ifpasswd) {
                    user_passwd = "";
                }
                if (user_username == null || user_username == "") {
                    user_username = "0";
                } else if (user_passwd == null || user_passwd == "") {
                    user_passwd = "0";
                } else if (user_nickname == null || user_nickname == "") {
                    user_nickname = "0";
                } else if (user_name == null || user_name == "") {
                    user_name = "0";
                } else if (user_sex == null || user_sex == "") {
                    user_sex = "0";
                } else if (user_age == null || user_age == "") {
                    user_age = "0";
                } else if (city_name == null || city_name == "") {
                    city_name = "0";
                }
                try {
                    String url = final_data.INFORMATION;
                    URL HttpUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) HttpUrl.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setReadTimeout(5000);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    String content = "user_username=" + user_username +
                            "&user_passwd=" + user_passwd +
                            "&user_nickname=" + user_nickname +
                            "&user_name=" + user_name +
                            "&user_sex=" + user_sex +
                            "&user_age=" + user_age +
                            "&city_name=" + city_name;
                    outputStream.write(content.getBytes());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String str;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        stringBuffer.append(str);
                    }
                    ifmt = stringBuffer.toString();
                    Log.d("ifmt-----------", ifmt);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

        /*if (ifmt.equals("true")) {

            //将信息存到本地
            SharedPreferences shared = getSharedPreferences("userfile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("user_username", user_username)
                    .putString("user_passwd", user_passwd)
                    .putString("user_nickname", user_nickname)
                    .putString("user_name", user_name)
                    .putString("user_sex", user_sex)
                    .putString("user_age", user_age)
                    .putString("city_name", city_name)
                    .putString("iflogin", "true")
                    .putString("information", "true");
            progressDialog.cancel();

            Toast.makeText(InformationActivity.this, "信息修改成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(CONTEXT_INCLUDE_CODE, intent.putExtra("username", user_username)
                    .putExtra("iflogin", "true"));
            finish();
        } else {
            progressDialog.cancel();
            Toast.makeText(InformationActivity.this, "嗷呜！居然失败了！", Toast.LENGTH_SHORT).show();
        }*/


    @Override
    public void onClick(View v) {
        Drawable bkDrawable = this.getResources ().getDrawable (R.drawable.layout_btoom_border);
        int i = v.getId ();
        if (i == R.id.information_return) {
            Intent intent = new Intent ();
            setResult (CONTEXT_INCLUDE_CODE, intent.putExtra ("username", user_username));
            finish ();
        } else if (i == R.id.information_finish) {
            informationText ();
        } else if (i == R.id.information_image) {
            Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
            intent.addCategory (Intent.CATEGORY_OPENABLE);
            intent.setType ("image/*");
            startActivity (intent);
        } else if (i == R.id.information_nickname) {
            information_nickname.setBackground (bkDrawable);
        } else if (i == R.id.information_name) {
            information_name.setBackground (bkDrawable);
        } else if (i == R.id.information_age) {
            information_age.setBackground (bkDrawable);
        } else if (i == R.id.information_sex) {
            information_sex.setBackground (bkDrawable);
        } else if (i == R.id.information_city) {
            information_city.setBackground (bkDrawable);
        } else if (i == R.id.information_passwd) {
            if (!topasswd) {
                senpasswd ();
            }
        }
    }

    private void senpasswd() {
        LayoutInflater inflater = (LayoutInflater)
                (InformationActivity.this.getSystemService (LAYOUT_INFLATER_SERVICE));
        View dialogPasswd = inflater.inflate (R.layout.passwd_dialog, null);
        passwd_edit = (EditText) dialogPasswd.findViewById (R.id.passwd_edit);
        final AlertDialog.Builder builder = new AlertDialog.Builder (InformationActivity.this)
                .setIcon (R.mipmap.logo)
                .setTitle ("输入原密码")
                .setView (dialogPasswd);
        builder.setNegativeButton ("取消", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton ("确认", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (passwd_edit.getText ().toString ().equals (passwd)) {
                    ifpasswd = true;
                    topasswd = true;
                    information_passwd.setBackgroundResource (R.drawable.layout_btoom_border);
                    Toast.makeText (InformationActivity.this, "密码正确", Toast.LENGTH_SHORT).show ();
                } else {
                    senpasswd ();
                    Toast.makeText (InformationActivity.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show ();
                }
            }
        });
        builder.create ().show ();
    }
}