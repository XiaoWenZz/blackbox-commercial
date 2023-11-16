package com.tencent.wxcloudrun.util;

// import org.springframework.stereotype.Component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

// @Component
public class FileUtil {

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

    public static void fileTraverse(File files){
        // 创建文件数组
        File[] arrFile = files.listFiles();

        //遍历文件数组（增强for循环）
        for(File file : arrFile){
            // 进行判断，判断遍历出的文件是否为文件目录
            if (file.isDirectory()){
                // 打印出文件的绝对路径
                System.out.println(file.getAbsolutePath());
                // 调用文件遍历的方法，再次遍历此文件目录下的文件
                fileTraverse(file);
            } else {
                /*判断为文件后，进行文件类型的判断（判断出自己所需的文件）*/
                // String 类型 变量，用于存放文件的后缀
                String suffix = "";
                // 获取文件名，类型为String
                String fileName = file.getName();
                // 用于存放最后一次出现 . 位置索引的变量
                int index = fileName.lastIndexOf("." );
                // 得到文件后缀名,index存放的是“.”的位置，往后+1 得到的就是文件后缀名
                suffix = fileName.substring(index + 1);
                // 获取文件的绝对路径，用于后面文件内容的读取
                File fileRoad = file.getAbsoluteFile();

                //进行判断是否为自己需要的文件
                if (suffix.equalsIgnoreCase("xlsx" )){

                }
            }
        }
    }

    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }


    /**
     * 删除path下所有文件包括文件夹
     * @param path
     * @param isIncludeRoot 是否要删除path(如果是文件夹)
     * @return true删除成功
     */
    public static boolean deleteAllFile(String path, boolean isIncludeRoot) {
        if(StringUtils.isBlank(path)) {
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            return false;
        }

        if(file.isFile()) {
            return file.delete();
        }

        File[] fileList = file.listFiles();
        boolean res = true;
        for (File f : fileList) {
            if(f.isFile()) {
                res = res && f.delete();
            } else if(f.isDirectory()) {
                res = res && deleteAllFile(f.getAbsolutePath(), true);
            }
        }

        if(isIncludeRoot) {
            res = res && file.delete();
        }

        return res;
    }



}
