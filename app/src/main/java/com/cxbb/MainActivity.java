package com.cxbb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wuba.uc.RsaCryptService;

public class MainActivity extends AppCompatActivity{

    EditText inputext ;
    Button wechatLogin;

    Button renative;
    private static final String APP_ID = "wx12345678";
    private IWXAPI api;

    static {
        System.loadLibrary("com_wuba_uc_rsa");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputext = findViewById(R.id.edit_test_input);

        wechatLogin = findViewById(R.id.wechatLogin);
        renative = findViewById(R.id.renativeBtn);






        wechatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api = WXAPIFactory.createWXAPI(MainActivity.this, APP_ID, true);
                api.registerApp(APP_ID);
                registerReceiver(new BroadcastReceiver() {
                                               @Override
                                               public void onReceive(Context context, Intent intent) {
                                                   api.registerApp(APP_ID);
                                               }
                                           },
                        new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
                doAuthorizing();
            }
        });

        renative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] bytes = new byte[]{116,97,111,116,97,111,53,50,48,49,51,49,52,77,84,1,56,53,50,56,57,48,101,99,56,50,54,52,56,98,99,102,-17,-68,
                        -125,97,110,100,114,111,105,100,-17,-68,-125,83,77,45,65,57,48,56,48,-17,-68,-125,49,50,-17,-68,-125,87,73,70,73,-17,-68,
                        -125,83,77,45,65,57,48,56,48,-17,-68,-125,122,104,-17,-68,-125,67,78,-17,-68,-125,115,97,109,115,117,110,103,-17,-68,
                        -125,50,49,57,56,95,49,48,56,48,-17,-68,-125,67,116,65,112,105,83,100,107,-17,-68,-125,49,48,46,49,51,46,50,-17,-68,
                        -125,71,77,84,43,48,56,58,48,48,-17,-68,-125,99,109,50,-17,-68,-125,-17,-68,-125,48,48,48,48,48,48,48,48,
                        48,48,48,48,48,48,48,48,-17,-68,-125,51,50,53,56,52,52,48,107,-17,-68,-125,-17,-68,-125,-17,-68,-125,60,117,110,
                        107,110,111,119,110,32,115,115,105,100,62,-17,-68,-125,49,57,50,46,49,54,56,46,48,46,49,48,55,-17,-68,-125,-17,
                        -68,-125,-17,-68,-125,49,-17,-68,-125,-17,-68,-125,48,-17,-68,-125,-17,-68,-125,51,49,-17,-68,-125,56,53,50,56,57,48,101,99,56,50,54,52,56,98,99,102,
                        -17,-68,-125,99,111,109,46,119,117,98,97,-17,-68,-125,-17,-68,-125};
                RsaCryptService.init(getApplicationContext());
                byte[] by = RsaCryptService.encrypt(bytes,bytes.length);
                String s = new String(by);
                System.out.println("加密数据:"+s);
            }
        });


    }
    //Android 平台应用授权登
    public void doAuthorizing() {
        if(api.isWXAppInstalled()) { //检测是否安装了微信
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo"; //应用授权作用域，如获取用户个人信息则填写 snsapi_userinfo
            req.state = "wx_getAuthorization"; //用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加 session 进行校验
            api.sendReq(req);
        }else {
            Toast.makeText(getApplicationContext(),"未安装微信",Toast.LENGTH_SHORT).show();
        }
    }
    public void  startService(View view)
    {
        String input = inputext.getText().toString();

        Intent serviceIntent = new Intent(this,ExampleService.class);

        serviceIntent.putExtra("inputExtra",input);

        ContextCompat.startForegroundService(this,serviceIntent);
    }


    public void  stopService(View view)
    {
        Intent serIntent = new Intent(this,ExampleService.class);
        stopService(serIntent);
    }

}