package cc.kq.jjvu.teakq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cc.kq.jjvu.entity.Teacher;
import cc.kq.jjvu.util.HttpUtil;
import cc.kq.jjvu.util.JsonToObject;
import cc.kq.jjvu.util.ObjectTojson;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText tea_num, tea_pwd;
    private Button sigin;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            String json = (String) msg.obj;
            after(json);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        tea_num = findViewById(R.id.tea_num);
        tea_pwd = findViewById(R.id.tea_pwd);
        sigin = findViewById(R.id.sigin);
    }

    public void login(View v) {
        if (tea_num.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
        } else if (tea_pwd.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Teacher tea = new Teacher();
            tea.setTeanum(tea_num.getText().toString().trim());
            tea.setTeapwd(tea_pwd.getText().toString().trim());
            final String logindata = new ObjectTojson().objecttojson(tea);
            HttpUtil httpUtil = new HttpUtil();
            httpUtil.doPost("app_tea_login", handler, logindata);

        }

    }
    public void after(String jsondata){

        if (jsondata == null || jsondata.equals("")) {
            Toast.makeText(LoginActivity.this, "失败！！！", Toast.LENGTH_SHORT).show();
        } else {
            Teacher tea1 = new Teacher();
            tea1 = new JsonToObject().jsonTojava(jsondata, Teacher.class);
            SharedPreferences.Editor editor = getSharedPreferences("tea", MODE_PRIVATE).edit();
            editor.putInt("id", tea1.getId());
            editor.putString("teanum", tea1.getTeanum());
            editor.putString("teapwd", tea1.getTeapwd());
            editor.putString("phone", tea1.getPhone());
            editor.putString("teaname", tea1.getTeaname());
            editor.commit();
            Toast.makeText(LoginActivity.this, "Sucess！！！", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Index.class));

        }
    }

}

