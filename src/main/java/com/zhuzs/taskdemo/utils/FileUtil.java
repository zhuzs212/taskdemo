package com.zhuzs.taskdemo.utils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * 文件 转换工具类
 *
 * @author zhu_zishuang
 * @date 7/13/21
 */
public class FileUtil {
 
    /**
     * 将 File 转换成 Byte数组
     *
     * @param pathStr
     * @return
     */
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 1024 * 10];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 File 转换成 Byte数组
     *
     * @param fis
     * @return
     */
    public static byte[] getBytesByFile(FileInputStream fis) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 1024 * 10];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 将 Byte数组 转换成 File
     *
     * @param bytes
     * @param filePath
     * @param fileName
     */
    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            // 判断文件目录是否存在
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(filePath + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * MultipartFile 转 File
     *
     * @param mFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile mFile, String filePathName) throws Exception {

        File file = null;
        if (ObjectUtil.isNull(mFile)) {
            throw new RuntimeException("图片不能为空！");
        } else {
            InputStream ins = null;
            ins = mFile.getInputStream();
            file = new File(filePathName);
            inputStreamToFile(ins, file);
            ins.close();
            return file;
        }
    }

    /**
     * File 转 MultipartFile
     *
     * @param file
     * @throws Exception
     */
    public static void fileToMultipartFile(File file) throws Exception {
//        FileInputStream fileInput = new FileInputStream(file);
//        MultipartFile toMultipartFile = new MockMultipartFile("file", file.getName(), "text/plain", getBytesByFile(fileInput));
//        toMultipartFile.getInputStream();
    }


    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   public static void main(String[] args) {
	   // 获取图片字节数组
	   byte[] bytesByFile = getBytesByFile("/Users/zhuzs/Downloads/Resources/picture/1.xuejing.jpg");
	   System.out.println("bytesByFile:"+ JSON.toJSONString(bytesByFile));
	   
	   // 输出到文件E:/test1/xxxx.jpg
	   getFileByBytes(bytesByFile, "/Users/zhuzs/Downloads/Resources/", "xxxx.jpg");
   }
}