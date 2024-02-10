package cxbb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

//Android技术生活-QQ交流群:723592501
public class WXAuthorization {
    private Activity mActivity;
    private final static String TAG= WXAuthorization.class.getName();
    private final static String APP_ID ="这里写你的appId";
    private IWXAPI api;
    public WXAuthorization(Activity activity) {
        mActivity = activity;
        api = WXAPIFactory.createWXAPI(mActivity, APP_ID, true);
        api.registerApp(APP_ID);
        mActivity.registerReceiver(new BroadcastReceiver() {
                                       @Override
                                       public void onReceive(Context context, Intent intent) {
                                           api.registerApp(APP_ID);
                                       }
                                   },
                new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
        doAuthorizing();
    }
    //Android 平台应用授权登
    public void doAuthorizing() {
        if(api.isWXAppInstalled()) { //检测是否安装了微信
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo"; //应用授权作用域，如获取用户个人信息则填写 snsapi_userinfo
            req.state = "wx_getAuthorization"; //用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止 csrf 攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加 session 进行校验
            api.sendReq(req);
        }else {
            Toast.makeText(mActivity.getApplicationContext(),"未安装微信",Toast.LENGTH_SHORT).show();
        }
    }
}


