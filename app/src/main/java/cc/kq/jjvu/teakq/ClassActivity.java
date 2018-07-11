package cc.kq.jjvu.teakq;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cc.kq.jjvu.entity.Kq;
import cc.kq.jjvu.entity.Student;
import cc.kq.jjvu.util.HttpUtil;

import static android.widget.Toast.LENGTH_SHORT;

public class ClassActivity extends AppCompatActivity {

    HttpUtil httpUtil;
    List<Student> list;
    ListView listView;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my);
        httpUtil=new HttpUtil();
        int classid=getIntent().getIntExtra("classid",0);
        httpUtil.doGet("app_findbyclassid?id="+classid,handler);



    }

    private void after2(String jsondata) {
        Kq kq=new Gson().fromJson(jsondata,Kq.class);
        new AlertDialog.Builder(ClassActivity.this)
                .setMessage("请假"+kq.getLevel()+"\n旷到"+kq.getAttendance())
                .create().show();
    }
    private void after(String jsondata) {
        list=new Gson().fromJson(jsondata,new TypeToken<List<Student>>(){}.getType());
        listView=findViewById(R.id.List_stu);
        List_stuadp listStuadp=new List_stuadp(this,list);
        listView.setAdapter(listStuadp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ClassActivity.this,""+position,Toast.LENGTH_LONG).show();
                String tea= getSharedPreferences("tea",MODE_PRIVATE).getString("teanum","") ;
                if (!tea.equals("")&&tea!=null) {
                    httpUtil.doGet("app_kq?stunum="+list.get(position).getStunum()+"&teanum="+tea,handler2);
                }
            }
        });
    }
}
