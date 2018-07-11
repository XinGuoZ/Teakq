package cc.kq.jjvu.teakq;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import cc.kq.jjvu.util.HttpUtil;

public class Index extends AppCompatActivity implements View.OnClickListener {
    MyFragment fg1;
    MyFragment_second fg2;
    MyFragment_three fg3;
    MyFragment_four fg4;
    //UI
    private TextView top_bar;
    private TextView bot1, bot2, bot3, bot4;
    private FrameLayout ly_content;
    private FragmentManager fManager;
    FragmentTransaction fTransaction;

    //接收第一个fragment的参数
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String jsondata=null;
            jsondata= (String) msg.obj;
            after(jsondata);
        }
    };

    Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String jsondata=null;
            jsondata= (String) msg.obj;
            after2(jsondata);
        }
    };
    Handler handler3=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String jsondata=null;
            jsondata= (String) msg.obj;
            after3(jsondata);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        fManager =getFragmentManager();
        bindViews();
        bot1.performLongClick();
    }

    private void bindViews() {
        top_bar = findViewById(R.id.txt_topbar);
        bot1 = findViewById(R.id.txt_channe1);
        bot2 = findViewById(R.id.txt_channe2);
        bot3 = findViewById(R.id.txt_channe3);
        bot4 = findViewById(R.id.txt_channe4);
        ly_content = findViewById(R.id.ly_content);

        bot1.setOnClickListener(this);
        bot2.setOnClickListener(this);
        bot3.setOnClickListener(this);
        bot4.setOnClickListener(this);

    }

    public void setSelect() {
        bot1.setSelected(false);
        bot2.setSelected(false);
        bot3.setSelected(false);
        bot4.setSelected(false);

    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);
    }

    @Override
    public void onClick(View v) {
        fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        SharedPreferences sharedPreferences=getSharedPreferences("tea",MODE_PRIVATE);
        int id=sharedPreferences.getInt("id",0);
        String teanum=sharedPreferences.getString("teanum","");
        HttpUtil httpUtil=new HttpUtil();
        switch (v.getId()) {
            case R.id.txt_channe1:

                httpUtil.doGet("app_findbyteaid?id="+id+"&teanum="+teanum,handler);

                break;
            case R.id.txt_channe2:

                httpUtil.doGet("app_course_all?teaid="+id,handler2);

                break;
            case R.id.txt_channe3:

                httpUtil.doGet("app_course_all?teaid="+id,handler3);

                break;
            case R.id.txt_channe4:
                setSelect();
                bot4.setSelected(true);
                if (fg4 == null) {
                    fg4=new MyFragment_four();
                    fTransaction.add(R.id.ly_content, fg4);
                } else {
                    fTransaction.show(fg4);
                }
                break;

        }

        //fTransaction.commit();
    }

    private void after3(String json) {
        setSelect();
        bot3.setSelected(true);
        if (fg3 == null&&json!=null&&!json.equals("")) {
            top_bar.setText("班级信息");
            fg3=new MyFragment_three();
            Bundle bundle=new Bundle();
            bundle.putString("list_cur",json);
            fg3.setArguments(bundle);
            fTransaction.add(R.id.ly_content, fg3);
        } else {
            fTransaction.show(fg3);
        }
        fTransaction.commit();
    }
    private void after2(String json) {
        //Toast.makeText(this,json,Toast.LENGTH_SHORT).show();
        setSelect();
        bot2.setSelected(true);
        if (fg2 == null&&json!=null&&!json.equals("")) {
            top_bar.setText("我的课表");
            fg2=new MyFragment_second();
            Bundle bundle=new Bundle();
            bundle.putString("list_cur",json);
            fg2.setArguments(bundle);
            fTransaction.add(R.id.ly_content, fg2);
        } else {
            fTransaction.show(fg2);
        }
        fTransaction.commit();
    }

    private void after(String json){
        //Toast.makeText(this,json,Toast.LENGTH_SHORT).show();
        setSelect();
        bot1.setSelected(true);
        if (fg1 == null&&json!=null&&!json.equals("")) {
            top_bar.setText("点到中...");
            fg1=new MyFragment();
            Bundle bundle=new Bundle();
            bundle.putString("list_stu",json);
            fg1.setArguments(bundle);
            fTransaction.add(R.id.ly_content, fg1);
        } else {
            //fTransaction.show(fg1);
            Toast.makeText(this,"没有点到信息",Toast.LENGTH_SHORT).show();
        }

            fTransaction.commit();
    }
}
