package com.zhuzs.taskdemo.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Base64 & file 互转工具类
 *
 * @author zhu_zishuang
 * @date 7/13/21
 */
public class Base64Utils {
    /**
     * 将文件进行base64编码
     *
     * @param filePath 文件路径
     * @return 文件编码后的字符串
     */
    public static String encode(String filePath) throws Exception {
        FileInputStream inputStream = new FileInputStream(filePath);

        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);

        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 将文件进行base64编码
     *
     * @param file 文件
     * @return 文件编码后的字符串
     */
    public static String encode(File file) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);

        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);

        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 将文件base64编码转为文件
     *
     * @param base64   文件base64编码
     * @param filePath 文件要保存的路径
     */
    public static void decode(String base64, String filePath) throws Exception {
        byte[] data = Base64.getDecoder().decode(base64);
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(data);
    }

    /**
     * 将MultipartFile 图片文件编码为base64
     *
     * @param file
     * @return
     */
    public static String generateBase64(MultipartFile file){
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("图片不能为空！");
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String contentType = file.getContentType();
        byte[] imageBytes = null;
        String base64EncoderImg="";
        try {
            imageBytes = file.getBytes();
            BASE64Encoder base64Encoder =new BASE64Encoder();
            /**
             * 1.Java使用BASE64Encoder 需要添加图片头（"data:" + contentType + ";base64,"）,
             *   其中contentType是文件的内容格式。
             * 2.Java中在使用BASE64Enconder().encode()会出现字符串换行问题,这是因为RFC 822中规定,
             *   每72个字符中加一个换行符号,这样会造成在使用base64字符串时出现问题,
             *   所以我们在使用时要先用replaceAll("[\\s*\t\n\r]", "")解决换行的问题。
             */
            base64EncoderImg = "data:" + contentType + ";base64," + base64Encoder.encode(imageBytes);
            base64EncoderImg = base64EncoderImg.replaceAll("[\\s*\t\n\r]", "");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return base64EncoderImg;
    }

    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}