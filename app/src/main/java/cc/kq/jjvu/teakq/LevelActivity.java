package cc.kq.jjvu.teakq;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cc.kq.jjvu.entity.Leave;
import cc.kq.jjvu.util.HttpUtil;

public class LevelActivity extends AppCompatActivity {
    HttpUtil httpUtil;
    Leave leave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        final List<Leave> list=new Gson().fromJson(getIntent().getStringExtra("leavejson"),new TypeToken<List<Leave>>(){}.getType());
        ListView listView=findViewById(R.id.list_leave);
        final List_levadp list_levadp=new List_levadp(this,list);
        listView.setAdapter(list_levadp);
        httpUtil=new HttpUtil();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new android.app.AlertDialog.Builder(LevelActivity.this)
                        .setMessage("请假事由：\n"+list.get(position).getLeavecontext()+"\n时间：\n"+list.get(position).getTime())
                        .setPositiveButton("批准", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                leave=list.get(position);
                                leave.setLeavstate(1);
                                httpUtil.Post("app_leave_update",new Gson().toJson(leave));
                                list.remove(position);
                                list_levadp.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("不批准", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                leave=list.get(position);
                                leave.setLeavstate(2);
                                httpUtil.Post("app_leave_update",new Gson().toJson(leave));
                                list.remove(position);
                                list_levadp.notifyDataSetChanged();
                            }
                        })
                        .create().show();
            }
        });
    }

}
