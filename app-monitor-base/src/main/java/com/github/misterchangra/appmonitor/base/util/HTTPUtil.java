package com.github.misterchangra.appmonitor.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtil {

    public static String doPost(String url, String jsonParam) {
        HttpURLConnection httpURLConnection = null;
        try {
            // 2. 创建HttpURLConnection对象
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            /* 3. 设置请求参数等 */
            // 请求方式  默认 GET
            httpURLConnection.setRequestMethod("POST");
            // 超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置是否输出
            httpURLConnection.setDoOutput(true);
            // 设置是否读入
            httpURLConnection.setDoInput(true);
            // 设置是否使用缓存
            httpURLConnection.setUseCaches(false);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            httpURLConnection.setInstanceFollowRedirects(true);
            // 设置请求头，测试此段代码可以省略
            //httpURLConnection.addRequestProperty("sysId","sysId");
            // 设置使用标准编码格式编码参数的名-值对,但以下方法都适用于json，似乎无效
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            //httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置传入参数的格式:请求参数应该是 json格式。
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            // 连接
            httpURLConnection.connect();
            /* 4. 处理输入输出 */
            // 写入参数到请求中
            //params是参数，内容是json格式
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(jsonParam.getBytes());
            // 简写方式
            //httpURLConnection.getOutputStream().write(params.getBytes());
            out.flush();
            //关闭
            out.close();
            // 从连接中读取响应信息
            StringBuilder msg = new StringBuilder();
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    msg.append(line + "\n");
                }
                //关闭
                reader.close();
            }
            return msg.toString();

        } catch (IOException e) {
            System.out.println("出错，错误信息："+e.getLocalizedMessage()+";"+e.getClass());
        }finally {
            // 5. 断开连接
            if (null != httpURLConnection){
                try {
                    httpURLConnection.disconnect();
                }catch (Exception e){
                    System.out.println("httpURLConnection 流关闭异常："+ e.getLocalizedMessage());
                }
            }
        }
        return null;
    }
}

