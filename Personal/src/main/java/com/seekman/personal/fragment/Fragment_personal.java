package com.seekman.personal.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seekman.library.utils.ImageLoad;
import com.seekman.library.utils.StringLoad;
import com.seekman.personal.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by alice on 2016/5/19.
 */
public class Fragment_personal extends Fragment {
    private static final String TAG = "Fragment_personal";
//个人
    /**
     * init()方法进行初始化
     */
    private View root = null;
    private ImageView personal_userImage, drawable_image, code_name_image;
    private TextView personal_userText, personal_item_friends,
            personal_userInformation, personal_userActivity,
            personal_userToActivity, personal_item_sett,
            personal_item_RQcode, personal_item_RQcodeSao, code_sao_text, code_name_text,
            personal_item_photos, personal_item_usercall, drawable_intent;
    private ListView dialogList;
    private String settextname;
    private Context context;

    //用户登录状态及登录用户
    // --iflogin,登录成功传递过来的iflogin,username
    // --ifloginto,usernameto主Activity传递过来的iflogin,username
    private String usernameto, username, user_image;
    private String ifloginto, iflogin, information;

    private Handler handler;

    //json数据存储
    Map<String, String> map = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i (TAG, "onCreateView: -------------------Fragment_square----------------------");
        root = inflater.inflate (R.layout.personal_main, null);

        /*new CentextThis().shareUserFile();*/

        init ();
        data ();
        setting ();
        lister ();
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        //登录成功发送过来的数据
        if (requestCode == 3 && resultCode == Activity.CONTEXT_INCLUDE_CODE) {
            username = data.getStringExtra ("username");
            iflogin = data.getStringExtra ("iflogin");

            SharedPreferences obj = getContext ().getSharedPreferences ("usefile", Context.MODE_APPEND);
            SharedPreferences.Editor edit = obj.edit ();
            edit.putString ("iflogin", "true");

            edit.commit ();
            Log.d (TAG, "onActivityResult: 登陆成功接收到username-------------" + username);
            if (iflogin.equals ("true")) {
                sharedInformation (username);
                //              personal_userText.setText(settextname);
//                Log.d("sharedInformation--------", iflogin);
            }
//            Log.d("iflogin--------", iflogin);
            Log.d ("username-------", username);
        }

        //二维码扫描成功发送过来的数据
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras ();
            String result = bundle.getString ("result");
            LayoutInflater codeSaoInflater = (LayoutInflater) getActivity ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            View codeSaoDialog = codeSaoInflater.inflate (R.layout.code_sao_dialog, null);
            code_sao_text = (TextView) codeSaoDialog.findViewById (R.id.code_sao_text);
            AlertDialog.Builder builder = new AlertDialog.Builder (getActivity ());
            builder.setNegativeButton ("确定", new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //不做代码执行
                }
            });
            code_sao_text.setText (result);
            builder.create ().show ();
        }
    }

    private void data() {
    }

    private void setting() {
    }

    //事件监听
    private void lister() {
        personal_userText.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                if (personal_userText.getText ().toString ().equals ("点击登录")) {
                    startActivityForResult (new Intent (getActivity (), LoginActivity.class), 3);
                } else {
                    startActivity (new Intent (getActivity (), InformationActivity.class));
                }
            }
        });

        personal_userImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
                intent.addCategory (Intent.CATEGORY_OPENABLE);
                intent.setType ("image/*");
                startActivity (intent);
            }
        });
        personal_userInformation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                /*String nickname = personal_userText.getText().toString();*/

                startActivity (new Intent (getActivity (), InformationActivity.class));
            }
        });

        personal_item_RQcode.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                RQcode ();
            }
        });
        personal_item_RQcodeSao.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                RQcodeSao ();
            }
        });

        personal_item_friends.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                drawable ();
            }
        });

        personal_item_sett.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                settDialog ();
            }
        });

        personal_item_photos.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
                intent.addCategory (Intent.CATEGORY_OPENABLE);
                intent.setType ("image/*");
                startActivity (intent);
            }
        });
        personal_item_usercall.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_INSERT);
                intent.setType ("vnd.android.cursor.dir/person");
                intent.setType ("vnd.android.cursor.dir/contact");
                intent.setType ("vnd.android.cursor.dir/raw_contact");
                startActivity (intent);
            }
        });
    }

    private void RQcodeSao() {
        startActivityForResult (new Intent (getActivity (), CaptureActivity.class), 0);
    }

    private void RQcode() {
        String mName = personal_userText.getText ().toString ();
        if (mName.equals ("点击登录")) {
            startActivityForResult (new Intent (getActivity (), LoginActivity.class), 3);
        } else {
            LayoutInflater CodeInflater = (LayoutInflater) getActivity ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            View codeDialog = CodeInflater.inflate (R.layout.code_name_dialog, null);
            code_name_text = (TextView) codeDialog.findViewById (R.id.code_name_text);
            code_name_image = (ImageView) codeDialog.findViewById (R.id.code_name_image);
            AlertDialog.Builder codeBuilder = new AlertDialog.Builder (getActivity ());
            Bitmap bitmp = EncodingUtils.createQRCode (mName, 500, 500,
                    BitmapFactory.decodeResource (getResources (), R.mipmap.logo));
            code_name_image.setImageBitmap (bitmp);
            codeBuilder.create ().show ();
        }
    }


    //下载对话框
    private void drawable() {
        LayoutInflater drawable_inflater = (LayoutInflater) getActivity ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View drawable_dialog = drawable_inflater.inflate (R.layout.drawable_dialog, null);
        drawable_image = (ImageView) drawable_dialog.findViewById (R.id.drawable_image);
        drawable_intent = (TextView) drawable_dialog.findViewById (R.id.drawable_intent);
        AlertDialog.Builder drawable_builder = new AlertDialog.Builder (getActivity ());
        drawable_builder.setView (drawable_dialog);
        drawable_intent.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent ().setAction (Intent.ACTION_VIEW)
                        .setData (Uri.parse (drawable_intent.getText ().toString ()))
                        .setClassName ("com.android.browser", "com.android.browser.BrowserActivity"));
            }
        });
        drawable_builder.create ().show ();
    }

    //设置对话框
    private void settDialog() {
        LayoutInflater inflater = (LayoutInflater) getActivity ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate (R.layout.personal_dialog, null);
        dialogList = (ListView) dialogView.findViewById (R.id.persional_dialog_list);
        AlertDialog.Builder builder = new AlertDialog.Builder (getActivity ());
        builder.setView (dialogView);
        dialogList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        LayoutInflater inflater1 = (LayoutInflater) getActivity ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
                        View dialogLayout = inflater1.inflate (R.layout.personal_dialog_image, null);
                        ImageView imageView = (ImageView) dialogLayout.findViewById (R.id.personal_dialog_image);
                        imageView.setImageResource (R.mipmap.logo);
                        TextView textView = (TextView) dialogLayout.findViewById (R.id.personal_dialog_text);
                        textView.setText ("Let's go 1.0.0");
                        AlertDialog.Builder imageBuilder = new AlertDialog.Builder (getActivity ());
                        imageBuilder.setView (dialogLayout);
                        imageBuilder.create ().show ();
                        break;
                    case 1:
                        Toast.makeText (getActivity (), "纳尼！居然是最新版！", Toast.LENGTH_SHORT).show ();
                        break;
                    case 2:
                        LayoutInflater inflater3 = (LayoutInflater) getActivity ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
                        View dialogEdit = inflater3.inflate (R.layout.passwd_dialog, null);
                        EditText editText = (EditText) dialogEdit.findViewById (R.id.passwd_edit);
                        AlertDialog.Builder editBunilder = new AlertDialog.Builder (getActivity ());
                        editBunilder.setIcon (R.mipmap.logo);
                        editBunilder.setTitle ("建议反馈");
                        editBunilder.setView (dialogEdit);
                        editBunilder.setNegativeButton ("取消", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //关闭Dialog
                            }
                        });
                        editBunilder.setPositiveButton ("确认", new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText (getActivity (), "我们会努力做得更好", Toast.LENGTH_SHORT).show ();
                            }
                        });
                        editBunilder.create ().show ();
                        break;
                    case 3:
                        SharedPreferences shared = getActivity ().getSharedPreferences ("userfile", Context.MODE_PRIVATE);
                        shared.edit ()
                                .clear ()
                                .commit ();
                        Toast.makeText (getActivity (), "退出成功", Toast.LENGTH_SHORT).show ();
                        startActivityForResult (new Intent (getActivity (), LoginActivity.class), 3);
                        break;
                }
            }
        });
        builder.create ().show ();
    }

    //控件初始化
    private void init() {

        String ifLogin = getContext ().getSharedPreferences ("userfile", Context.MODE_PRIVATE).getString ("iflogin", "false");

        if (getArguments () != null) {
            ifloginto = getArguments ().getString ("iflogin");
            usernameto = getArguments ().getString ("username");
            user_image = getArguments ().getString ("user_image");
            information = getArguments ().getString ("information");
        }
        if (ifloginto.equals ("flase")) {
            sharedInformation (usernameto);
            new HttpImage (user_image, handler, personal_userImage);
        }
//        Log.d("ifloginto--------", ifloginto);
        Log.d ("usernameto-------", usernameto);

        personal_userImage = (ImageView) root.findViewById (R.id.personal_userImage);

        personal_userText = (TextView) root.findViewById (R.id.personal_userText);

        personal_userInformation = (TextView) root.findViewById (R.id.personal_userInformation);
        personal_userActivity = (TextView) root.findViewById (R.id.personal_userActivity);
        personal_userToActivity = (TextView) root.findViewById (R.id.personal_userToActivity);
        personal_item_friends = (TextView) root.findViewById (R.id.personal_item_friends);

        personal_item_RQcode = (TextView) root.findViewById (R.id.personal_item_RQcode);
        personal_item_RQcodeSao = (TextView) root.findViewById (R.id.personal_item_RQcodeSao);

        personal_item_sett = (TextView) root.findViewById (R.id.personal_item_sett);

        personal_item_photos = (TextView) root.findViewById (R.id.personal_item_photos);
        personal_item_usercall = (TextView) root.findViewById (R.id.personal_item_usercall);
    }

    //通过用户名查找用户信息并显示到UI
    private void sharedInformation(final String username) {
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
        personal_userText.setText (rson.getUser_Nickname ());
        personal_userImage.setTag (rson.getUser_Images ());
        new ImageLoad (personal_userImage).execute (rson.getUser_Images ());

    }
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    String url = final_data.SHAREDINFORMATION;
//                    URL Httpurl = new URL(url);
//                    HttpURLConnection con = (HttpURLConnection) Httpurl.openConnection();
//                    con.setRequestMethod("POST");
//                    con.setReadTimeout(5000);
//                    OutputStream out = con.getOutputStream();
//                    String content = "user_username=" + username;
//                    out.write(content.getBytes());
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    String string;
//                    StringBuffer sb = new StringBuffer();
//                    while ((string = reader.readLine()) != null) {
//                        sb.append(string);
//                    }
//                    Log.d("sharedInformation", sb.toString());
//
//                    parseJson(sb.toString(), username);
//                    /*new Handler(){
//
//                    };*/
//                    String imagePath;
//                    imagePath = map.get("user_Images");
//                    new HttpImage(imagePath, handler, personal_userImage);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }.start();
//    }


    //解析服务器发送过来的Json数据
//    private Map<String, String> parseJson(String json, String username) {
//        try {
//            //读取JSON对象
//            JSONObject object = new JSONObject(json);
//
//            String user_Username = object.getifloginString("user_Username");
//
//            if (user_Username.equals(username)) {
//                String user_Passwd = object.getString("user_Passwd");
//                String user_Name = object.getString("user_Name");
//                if (user_Name.equals("null")) {
//                    user_Name = "";
//                }
//                String user_Nickname = object.getString("user_Nickname");
//                String user_Sex = object.getString("user_Sex");
//                if (user_Sex.equals("null")) {
//                    user_Sex = "";
//                }
//                String user_Age = object.getString("user_Age");
//                if (user_Age == "0") {
//                    user_Age = "";
//                }
//                String user_Images = object.getString("user_Images");
//                String city_Name = object.getString("city_Name");
//                if (city_Name.equals("null")) {
//                    city_Name = "";
//                }
//
//                map = new HashMap<String, String>();
//                map.put("user_Username", user_Username);
//                map.put("user_Passwd", user_Passwd);
//                map.put("user_Name", user_Name);
//                map.put("user_Nickname", user_Nickname);
//                map.put("user_Sex", user_Sex);
//                map.put("user_Age", user_Age);
//                map.put("user_Images", user_Images);
//                map.put("city_Name", city_Name);
//
//                Log.d("parseJson----------", map.toString());
////                personal_userText.setText(map.get("user_Nickname"));
//                settextname = map.get("user_Nickname");
//                System.out.print("========================"+settextname);
////                setUserText(personal_userText,name);
////                personal_userText.setText(name);
//                return map;
//            } else {
//                Toast.makeText(getContext(), "获取不到数据", Toast.LENGTH_SHORT).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;


    private void showShare() {
        ShareSDK.initSDK (getActivity ());
        OnekeyShare oks = new OnekeyShare ();
        //关闭sso授权
        oks.disableSSOWhenAuthorize ();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle (getString (R.string.ssdk_oks_multi_share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl ("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText ("我是分享文本，啦啦啦~");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath ("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl ("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment ("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite (getString (R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl ("http://sharesdk.cn");
// 启动分享GUI
        oks.show (getActivity ());
    }
}