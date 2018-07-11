package cc.kq.jjvu.teakq;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cc.kq.jjvu.entity.Leave;

/**
 * Created by master on 2017/11/8.
 */

public class List_levadp extends BaseAdapter {
    private Context context;
    private List<Leave> list;
    private LayoutInflater layoutInflater;

    public List_levadp(Context context, List<Leave> list) {
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
            convertView = layoutInflater.inflate(R.layout.list_stu, null);
            viewHolder.stu_num = convertView.findViewById(R.id.stu_num);
            viewHolder.stu_name = convertView.findViewById(R.id.stu_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Leave stu = list.get(position);
        viewHolder.stu_num.setText(stu.getStunum());
        viewHolder.stu_name.setText(stu.getStuname());
        return convertView;
    }

    private static class ViewHolder {
        public TextView stu_num;
        public TextView stu_name;

    }
}
