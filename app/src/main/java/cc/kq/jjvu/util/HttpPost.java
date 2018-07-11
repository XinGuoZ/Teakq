package cc.kq.jjvu.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by master on 2017/11/4.
 */

public class HttpPost {


    private String ip = "http://10.16.112.186/";
    private String url;
    private String json;

    public HttpPost(String json, String url) {
        this.json = json;
        this.url = ip + url;
    }


    public String doPost() {

        String Msg = "";
        try {
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            //设置响应时间
            conn.setReadTimeout(5000);
            //设置请求的方法
            conn.setRequestMethod("POST");
            //设置连接时间
            conn.setConnectTimeout(5000);
            //设置输入流
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //设置不允许缓存
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            //传输请求的参数
            OutputStream out = conn.getOutputStream();
            out.write(json.getBytes());
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                Msg = new String(message.toByteArray());
                return Msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Msg;
    }

}
