package cc.kq.jjvu.teakq;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import java.util.logging.Level;

import cc.kq.jjvu.util.HttpUtil;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID_1 = 1;
    private Bitmap LargeBitmap = null;
    private NotificationManager myManager = null;
    private Notification myNotification;
    private Context mContext;
    Handler handler=new Handler(){


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String jsondata=null;
            jsondata= (String) msg.obj;

            after(jsondata);
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("tea", MODE_PRIVATE);
        String teanum = null;
        teanum = preferences.getString("teanum", "");
        //状态栏通知
        mContext = MainActivity.this;
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //1.从系统服务中获得通知管理器
        myManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Onpenotification("");



        if (teanum == null || teanum.equals("")) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    MainActivity.this.finish();
                }
            }, 1000);
        } else {
            new HttpUtil().doGet("app_leave_list?teanum="+teanum,handler);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, Index.class));
                    MainActivity.this.finish();
                }
            }, 1000);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void after(String jsondata) {
        if(!jsondata.equals("")&&jsondata!=null){
            Onpenotification(jsondata);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Onpenotification(String json) {
        //3.定义一个PendingIntent，点击Notification后启动一个Activity
        PendingIntent pi = PendingIntent.getActivity(
                mContext,
                100,
                new Intent(mContext, LevelActivity.class).putExtra("leavejson",json),
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        //2.通过Notification.Builder来创建通知
        Notification.Builder myBuilder = new Notification.Builder(mContext);
        myBuilder.setContentTitle("考勤客户端")
                .setContentText("您有请假的信息")
                //.setSubText("这是补充小行内容")
                .setTicker("您收到新的消息")
                //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(LargeBitmap)
                //设置默认声音和震动
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)//点击后取消
                .setWhen(System.currentTimeMillis())//设置通知时间
                .setPriority(Notification.PRIORITY_HIGH)//高优先级
                //.setVisibility(Notification.VISIBILITY_PUBLIC)
                //android5.0加入了一种新的模式Notification的显示等级，共有三种：
                //VISIBILITY_PUBLIC  只有在没有锁屏时会显示通知
                //VISIBILITY_PRIVATE 任何情况都会显示通知
                //VISIBILITY_SECRET  在安全锁和没有锁屏的情况下显示通知
                .setContentIntent(pi);  //3.关联PendingIntent
        myNotification = myBuilder.build();
        //4.通过通知管理器来发起通知，ID区分通知
        myManager.notify(NOTIFICATION_ID_1, myNotification);

    }

}
