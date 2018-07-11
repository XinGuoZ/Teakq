package cc.kq.jjvu.teakq;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cc.kq.jjvu.entity.Course;
import cc.kq.jjvu.entity.Student;

/**
 * Created by master on 2017/11/8.
 */

public class List_classadp extends BaseAdapter {
    private Context context;
    private List<Course> list;
    private LayoutInflater layoutInflater;

    public List_classadp(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_class, null);
            viewHolder.coursename = convertView.findViewById(R.id.course_name);
            viewHolder.classname = convertView.findViewById(R.id.class_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Course course = list.get(position);
        viewHolder.coursename.setText(course.getCoursename());
        viewHolder.classname.setText(course.getClassname());
        return convertView;
    }

    private static class ViewHolder {
        public TextView coursename;
        public TextView classname;

    }
}
