package com.jinlink;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;

public class QiNiuUpload {
    private static String accessKey="O5lgHoK-rbColK4V75WielL0CADFM_wYKal7UOYz";

    private static final String secretKey="tTTxObEY4TaRu-cvzDyFS0Gpy_ZKFejrxH46WKQY";

    private static String bucket="blue-archive";
    private static void upload(String key, String localPath) {
        //根据自己的对象空间的地址选（华南）
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);
        //你7牛云的accessKey和secretKey 填进去进行
        Auth auth = Auth.create(accessKey, secretKey);
        //你7牛云的空间名称
        String upToken = auth.uploadToken(bucket);
        try {
            //开始上传
            Response response = uploadManager.put(localPath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
    //实现上传的方法file为本地上传的文件夹
    private static void uploadFiles(File file) {
        //对该文件夹下的文件收集起来
        File[] files = file.listFiles();
        for (File file1 : files) {
            //如果是文件则进入
            if (file1.isFile()) {
                //对key进行构造生成原来文件夹的结构
                String key  = "";
                //获取该文件的地址
                String path = file.getAbsolutePath() + "\\" + file1.getName();
                /**例如D:\element-ui\package.json的文件地址
                 * 则要想有一样的目录结构则key应为element-ui/package.json
                 * 对path进行剔除\生成element-ui/package.json该格式
                 */
                String[] split = path.split("\\\\");
                for (int i = 1; i < split.length; i++) {
                    if (key.equals("")) {
                        key = split[i];
                    } else {
                        key = key + "/" + split[i];
                    }
                }
                //进行上传
                upload(key, path);
            }
            //如果是文件夹则进入该文件夹递归调用
            else {
                uploadFiles(file1);
            }
        }
    }
    //测试方法
    public static void main(String[] args) throws Exception {
        //上传文件夹的地址
        File file = new File("D:\\jinLink\\uploadPath");
        //实现上传
        uploadFiles(file);
    }
}
