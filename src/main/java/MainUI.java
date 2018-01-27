import com.alibaba.fastjson.JSON;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MainUI {
    /**
     * 发送HttpPost请求
     *
     * @param strURL
     *            服务地址
     * @param params
     *
     * @return 成功:返回json字符串<br/>
     */
    public static void sendPost(String strURL, Map<String, String> params, int i) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
    //            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(JSON.toJSONString(params));
            out.flush();
            out.close();

            int code = connection.getResponseCode();
            InputStream is = null;
            if (code == 200) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            // 读取响应
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int length = (int) connection.getContentLength();// 获取长度
            int total = 0;
            File f = new File("hehehe.png");
            if (length != -1) {

                FileOutputStream fos = new FileOutputStream(f);
                //fos.write("aaa".getBytes(), 0, "aaa".getBytes().length);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf, 0, 1024)) != -1) {
                    bos.write(buf, 0, len);
                    fos.write(buf, 0, len);
                    total += len;
                }
                fos.flush();
                fos.close();
            }

            ImageTset(bos.toByteArray(), i);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ImageTset(byte[] file, int i) throws Exception {
        System.out.println(file.length);
        long start = System.currentTimeMillis();

        ImageInputStream imageInputstream = new MemoryCacheImageInputStream(new ByteArrayInputStream(file));

        final Image src1 = javax.imageio.ImageIO.read(imageInputstream);

//        //获取图片的宽度
        final int width = src1.getWidth(null);
        //获取各个图像的高度
        final int height1 = src1.getHeight(null);

        //构造一个类型为预定义图像类型之一的 BufferedImage。 宽度为第一只的宽度，高度为各个图片高度之和
        BufferedImage tag = new BufferedImage(width, height1 + 40, BufferedImage.TYPE_INT_RGB);
        //创建输出流

        //绘制合成图像
        final Graphics g = tag.getGraphics();

        FutureTask<Void> futureTask = new FutureTask<Void>(new Callable<Void>() {
            public Void call() throws Exception {
                g.setColor(Color.BLACK);//文字颜色
                g.setFont(new Font("微软雅黑",Font.BOLD,20));//设置字体
                g.drawString("HanyuKing 中国会议室", 10, 20);
                return null;
            }
        });
        new Thread(futureTask).start();

        FutureTask<Void> futureTask1 = new FutureTask<Void>(new Callable<Void>() {
            public Void call() throws Exception {
                g.drawImage(src1, 0, 40, width, height1, null);

                g.setColor(Color.white);//设置颜色,可以省略
                g.fill3DRect(0, 0, width, 40, true);
                g.drawRect(0, 40 - 1, width, 2);
                g.drawRect(width - 1, 0, 2, 40);
                return null;
            }
        });
        new Thread(futureTask1).start();

        //g.drawChars("aaaaa".toCharArray(), 0, 5, 10, 10);;
        // 释放此图形的上下文以及它使用的所有系统资源。
        futureTask.get();
        futureTask1.get();
        g.dispose();

        ByteArrayOutputStream fileByte = new ByteArrayOutputStream();
        ImageIO.write(tag, "png", fileByte);

//        FileOutputStream out = new FileOutputStream(i + "treasureMap.png");
//        out.write(fileByte.toByteArray());
        //关闭输出流
//        out.close();
        System.out.println("藏宝图出来了");
        System.out.println(fileByte.toByteArray().length);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) throws Exception {
//        ImageTset();
        final String urlParam = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=6_2hFNrlvTxrUUmxsgMl8-y9gaW-oyGSW730bXO3jbN-AJAwBhvJPxQO7KNqCFmobkhVGpZ3mFKlS4YgckH14ZC5anzBdqjIAtvKr2CKm0_AOY34h_ZcfIubAZhWazVpgr9ZHPa5Ll-tr41sCeULJfAGAQHF";
        final Map<String, String> map = new HashMap<String, String>();
        map.put("path", "pages/index/index");
        map.put("scene", "name=hanyuking");

        sendPost(urlParam, map, 0);

//        for(int i = 0; i < 10; i++) {
//            final int j = i;
//            new Thread(new Runnable() {
//                public void run() {
//
//                }
//            }).start();
//        }
    }
}