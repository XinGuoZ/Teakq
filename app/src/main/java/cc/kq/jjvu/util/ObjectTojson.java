package cc.kq.jjvu.util;

import com.google.gson.Gson;

import java.util.List;


/**
 * @author master
 *
 */
public class ObjectTojson {
    Gson gson =new Gson();

    public <T>String objecttojson(T t){
        String json=gson.toJson(t);
        return json;
    }

    public <T> String listtojson(List<T> list){
        String json=gson.toJson(list);
        return json;
    }


}
