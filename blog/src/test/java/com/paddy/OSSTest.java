package com.paddy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OSSTest {
    @Test
    public void testOss() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }

    }
}
