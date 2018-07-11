package cc.kq.jjvu.teakq;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cc.kq.jjvu.entity.Attendance;
import cc.kq.jjvu.entity.Student;
import cc.kq.jjvu.util.HttpUtil;

public class MyFragment extends Fragment {




    public MyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my,container,false);
        Bundle bundle = getArguments();
        final ListView listView=view.findViewById(R.id.List_stu);
        final List<Student> list =new Gson().fromJson(bundle.getString("list_stu"), new TypeToken<List<Student>>() {}.getType());
        //List<Student> list=new JsonToObject().jsonTolist(bundle.getString("list_stu"),Student.class);
        final List_stuadp listStuadp=new List_stuadp(getActivity(),list);
        listView.setAdapter(listStuadp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("请操作");
                builder.setNegativeButton("到勤", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        listStuadp.notifyDataSetChanged();
                    }
                });

                builder.setPositiveButton("旷到", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("tea",Context.MODE_PRIVATE);
                        Attendance attendance=new Attendance();
                        attendance.setTeaNum(sharedPreferences.getString("teanum",""));
                        attendance.setStuNum(list.get(position).getStunum());
                        attendance.setStuName(list.get(position).getStuname());
                        new HttpUtil().Post("app_abs_add",new Gson().toJson(attendance));
                        list.remove(position);
                        listStuadp.notifyDataSetChanged();
                    }
                });
                builder.create().show();
                Toast.makeText(getActivity(), "Click item" + position+id, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
