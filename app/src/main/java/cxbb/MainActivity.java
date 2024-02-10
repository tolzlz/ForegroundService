package cxbb;

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

import com.example.foregroundservice.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MainActivity extends AppCompatActivity{

    EditText inputext ;
    Button wechatLogin;
    private static final String APP_ID = "wx12345678";
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputext = findViewById(R.id.edit_test_input);

        wechatLogin = findViewById(R.id.wechatLogin);

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