package client.model.util;

import client.model.server.ClientStatus;
import common.Tip;

import java.io.*;

/**
 * @author Tptogiar
 * @Descripiton: 文件栏工具
 * @creat 2021/05/23-09:29
 */


public class FileUtil {

    public static byte[] toByteArray(File file) throws IOException {

        BufferedInputStream bis = null;
        ByteArrayOutputStream bos=null;
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }

        bos = new ByteArrayOutputStream((int) file.length());
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while (-1 != (len = bis.read(buffer, 0, bufferSize))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            bis.close();
            bos.close();
        }
    }


    public static void getFile(byte[] fileBytes, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File dir = null;
        try {
            dir = new File(filePath);
            if(!dir.exists()){
                dir.getParentFile().mkdirs();
                dir.createNewFile();
            }
            fos = new FileOutputStream(dir);
            bos = new BufferedOutputStream(fos);
            bos.write(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
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

}
