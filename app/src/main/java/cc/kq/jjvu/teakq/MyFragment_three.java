package cc.kq.jjvu.teakq;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cc.kq.jjvu.entity.Course;


public class MyFragment_three extends Fragment {

    public MyFragment_three() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_three, container, false);
        Bundle bundle =getArguments();
        final List<Course> list=new Gson().fromJson(bundle.getString("list_cur"), new TypeToken<List<Course>>() {}.getType());
        ListView listView=view.findViewById(R.id.list_class);
        List_classadp list_classadp=new List_classadp(getActivity(),list);
        listView.setAdapter(list_classadp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),ClassActivity.class);
                intent.putExtra("classid",list.get(position).getClassid());
                startActivity(intent);
            }
        });
        return view;
    }

}
