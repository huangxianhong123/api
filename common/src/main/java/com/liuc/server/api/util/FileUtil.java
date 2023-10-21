package com.liuc.server.api.util;


import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

@Slf4j
public class FileUtil {

    static MessageDigest MD5 = null;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            log.error(ne.getMessage());
        }
    }

    /**
     * 对一个文件获取md5值
     *
     * @return md5串
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 清空文件夹内的文件
     *
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decodeBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = org.apache.commons.codec.binary.Base64.decodeBase64(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 将base64字符保存文本文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void base64ToFile(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static void copyFile(String oldfilepath, String newpath, boolean rewrite) {
        File oldfile = new File(oldfilepath);
        File newfile = new File(newpath + File.separator + oldfile.getName());
        if (!oldfile.exists() || !oldfile.isFile()) {
            return;
        }
        if (newfile.exists()) {//新文件路径下有同名文件
            if (!rewrite) {
                newfile.delete();
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            } else {
                newfile = new File(newpath + File.separator + "(1)" + newfile.getName());
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        } else {
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        try {
            FileInputStream fin = new FileInputStream(oldfile);//输入流
            try {
                FileOutputStream fout = new FileOutputStream(newfile, true);//输出流
                byte[] b = new byte[1024];
                try {
                    while ((fin.read(b)) != -1) {//读取到末尾 返回-1 否则返回读取的字节个数
                        fout.write(b);
                    }
                    fin.close();
                    fout.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }

            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public static void copyFile(File oldfile, String newpath, boolean rewrite) {
        File newfile = new File(newpath + File.separator + oldfile.getName());
        if (!oldfile.exists() || !oldfile.isFile()) {
            return;
        }
        if (newfile.exists()) {//新文件路径下有同名文件
            if (!rewrite) {
                newfile.delete();
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            } else {
                newfile = new File(newpath + File.separator + "(1)" + newfile.getName());
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        } else {
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        try {
            FileInputStream fin = new FileInputStream(oldfile);//输入流
            try {
                FileOutputStream fout = new FileOutputStream(newfile, true);//输出流
                byte[] b = new byte[1024];
                try {
                    while ((fin.read(b)) != -1) {//读取到末尾 返回-1 否则返回读取的字节个数
                        fout.write(b);
                    }
                    fin.close();
                    fout.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }

            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 将文件转成base64字符串
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return BaseEncoding.base64().encode(buffer);
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(@RequestParam MultipartFile file) throws Exception {
        InputStream ins = null;
        ins = file.getInputStream();
        File toFile = new File(file.getOriginalFilename());
        inputStreamToFile(ins, toFile);
        ins.close();
        return toFile;
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


    /**
     * 获取图片宽度和高度
     *
     * @param file
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            // 获得文件输入流
            is = new FileInputStream(file);
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图片宽
            result[1] = src.getHeight(null);// 得到源图片高
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return result;
    }

    public static void saveFile(InputStream inputStream, String filePath, String fileName) {
        OutputStream os = null;
        try {
            // 保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String jsonRead(File file) {
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

}
