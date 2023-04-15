package com.meiling.oms.jpush;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.meiling.common.BuildConfig;
import com.meiling.common.utils.MMKVUtils;
import com.meiling.oms.R;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsClientConfig;
import com.taobao.agoo.TaobaoRegister;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import retrofit2.http.Url;

//import org.android.agoo.huawei.HuaWeiRegister;
//import org.android.agoo.mezu.MeizuRegister;
//import org.android.agoo.oppo.OppoRegister;
//import org.android.agoo.vivo.VivoRegister;
//import org.android.agoo.xiaomi.MiPushRegistar;mm

/**
 * PushSDK集成帮助类
 */
public class PushHelper {


    private static final String TAG = PushHelper.class.getSimpleName();
    public static String deviceTokenStr;

    /**
     * 预初始化，已添加子进程中初始化sdk。
     * 使用场景：用户未同意隐私政策协议授权时，延迟初始化
     *
     * @param context 应用上下文
     */
    public static void preInit(Context context) {
        try {
            Log.e("TAG", "register 1111：--> ");
            //解决推送消息显示乱码的问题
            AccsClientConfig.Builder builder = new AccsClientConfig.Builder();
            builder.setAppKey("umeng:" + PushConstants.APP_KEY);
            builder.setAppSecret(PushConstants.MESSAGE_SECRET);
            builder.setTag(AccsClientConfig.DEFAULT_CONFIGTAG);
            ACCSClient.init(context, builder.build());
            TaobaoRegister.setAccsConfigTag(context, AccsClientConfig.DEFAULT_CONFIGTAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        UMConfigure.preInit(context, BuildConfig.UmPushAppKey, PushConstants.CHANNEL);
        UMConfigure.preInit(context, PushConstants.APP_KEY, PushConstants.CHANNEL);
        if (!isMainProcess(context)) {
            init(context);
        }
    }

    /**
     * 初始化。
     * 场景：用户已同意隐私政策协议授权时
     *
     * @param context 应用上下文
     */
    public static void init(Context context) {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息
//        UMConfigure.init(
//                context,
//                BuildConfig.UmPushAppKey,
//                PushConstants.CHANNEL,
//                UMConfigure.DEVICE_TYPE_PHONE,
//                BuildConfig.UmPushMessageSecret
//        );
        UMConfigure.init(
                context,
                PushConstants.APP_KEY,
                PushConstants.CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE,
                PushConstants.MESSAGE_SECRET
        );

        //获取消息推送实例
        final PushAgent pushAgent = PushAgent.getInstance(context);

        pushAdvancedFunction(context);

        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {

            @SuppressLint("CommitPrefEdits")
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("TAG", "deviceToken --> " + deviceToken);
                deviceTokenStr = deviceToken;
                MMKV.defaultMMKV().putString("UmengToken", deviceTokenStr);
//                SharedPreferences sharedPreferences = AppConfig.INSTANCE.getApplication().getSharedPreferences("cashier_file", Context.MODE_PRIVATE);
//                sharedPreferences.edit().putString("deviceToken", deviceToken).apply();

                //可设置别名，推送时使用别名推送
                String type = "uid";
                String alias = "1234567890";
                pushAgent.setAlias(alias, type, new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean success, String message) {
                        Log.i("TAG", "setAlias " + success + " msg:" + message);

                    }
                });
            }

            @Override
            public void onFailure(String errCode, String errDesc) {
                Log.e("TAG", "register failure：--> " + "code:" + errCode + ",desc:" + errDesc);
            }
        });
        if (isMainProcess(context)) {
//            registerDeviceChannel(context);
        }
    }

    /**
     * 注册设备推送通道（小米、华为等设备的推送）
     *
     * @param context 应用上下文
     */
//    private static void registerDeviceChannel(Context context) {
//        //小米通道，填写您在小米后台APP对应的xiaomi id和key
//        MiPushRegistar.register(context, PushConstants.MI_ID, PushConstants.MI_KEY);
//        //华为，注意华为通道的初始化参数在minifest中配置
//        HuaWeiRegister.register((Application) context.getApplicationContext());
//        //魅族，填写您在魅族后台APP对应的app id和key
//        MeizuRegister.register(context, PushConstants.MEI_ZU_ID, PushConstants.MEI_ZU_KEY);
//        //OPPO，填写您在OPPO后台APP对应的app key和secret
//        OppoRegister.register(context, PushConstants.OPPO_KEY, PushConstants.OPPO_SECRET);
//        //vivo，注意vivo通道的初始化参数在minifest中配置
//        VivoRegister.register(context);
//    }

    /**
     * 是否运行在主进程
     *
     * @param context 应用上下文
     * @return true: 主进程；false: 子进程
     */
    public static boolean isMainProcess(Context context) {
        return UMUtils.isMainProgress(context);
    }

    private static void pushAdvancedFunction(Context context) {
        //推送高级功能集成说明
        PushAgent pushAgent = PushAgent.getInstance(context);

        //设置通知栏显示通知的最大个数（0～10），0：不限制个数
        pushAgent.setDisplayNotificationNumber(0);

//        pushAgent.setPushIntentServiceClass();

        Handler handler = new Handler(context.getMainLooper());
        //推送消息处理
        UmengMessageHandler msgHandler = new UmengMessageHandler() {
            //处理通知栏消息
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                super.dealWithNotificationMessage(context, msg);
                Log.i("lwq", "notification receiver:" + msg.getRaw().toString());
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        if (msg.extra != null) {
                            Log.i("lwq", "notification receiver:" + msg.toString());
//                            JpushKt.createAndStart(context, msg, 10, 10);
                        }
                    }
                });
            }

            //自定义通知样式，此方法可以修改通知样式等
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                /**
                 * context:上下文
                 * uMessage:表示当前传递过来的消息，在消息中，我们通过变量builder_id判断使用哪种样式
                 */
                //创建通知栏对象

                // 显示通知
                Notification.Builder builder = new Notification.Builder(context);
                @SuppressLint("RemoteViewLayout") RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.item_message_center);
                remoteView.setTextViewText(R.id.txt_msg_center_name, msg.title);
                remoteView.setTextViewText(R.id.txt_msg_center_spec, msg.text);

                builder.setContent(remoteView)
                        .setSmallIcon(R.mipmap.logo)
                        .setTicker(msg.ticker)
                        .setAutoCancel(true);
                return builder.getNotification();

            }

            //处理透传消息
            @Override
            public void dealWithCustomMessage(Context context, UMessage msg) {
                Log.i("lwq", "notification 1111111111receiver:" + msg.getRaw().toString());
                super.dealWithCustomMessage(context, msg);

            }
        };
        pushAgent.setMessageHandler(msgHandler);

        //推送消息点击处理
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
                Log.i(TAG, "click openActivity: " + msg.getRaw().toString());
            }

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
                Log.i(TAG, "click launchApp: " + msg.getRaw().toString());
            }

            @Override
            public void dismissNotification(Context context, UMessage msg) {
                super.dismissNotification(context, msg);
                Log.i(TAG, "click dismissNotification: " + msg.getRaw().toString());
            }
        };
        pushAgent.setNotificationClickHandler(notificationClickHandler);
    }

//    private void initChannel(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            if (nm != null) {
//                NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup("MyGroupId", "自定义通知组");
//                nm.createNotificationChannelGroup(notificationChannelGroup);
//                NotificationChannel notificationChannel = new NotificationChannel("MyChannelId", "自定义通知", NotificationManager.IMPORTANCE_HIGH);
//                notificationChannel.setGroup("MyGroupId");
//                notificationChannel.enableLights(true);
//                notificationChannel.enableVibration(true);
//                notificationChannel.setSound(Uri.parse("android:resource://" + context.getPackageName() + "/" + R.raw.new_order), null);    // 设置自定义铃声
//                nm.createNotificationChannel(notificationChannel);
//            }
//        }
//    }

}
