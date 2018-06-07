package com.bugjc.sftp;

import com.bugjc.sftp.service.SftpService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SftpApplicationTests {

    @Autowired
    private SftpService sftpService;

    @Test
    public void listAllFile(){
        sftpService.listAllFile("*");
    }


    @Test
    public void uploadFile() {
        File file = new File("/Users/qingyang/Desktop/aoki/glcxw/upc-parent/README.md");
        sftpService.uploadFile(file);
        Assert.assertTrue(sftpService.existFile("/upload/README.md"));
    }

    @Test
    public void downLoadFile(){
        File downloadFile = sftpService.downloadFile("*_20180507.csv", "/Users/qingyang/Desktop/777.csv");
        Assert.assertTrue(downloadFile.exists());
    }

    @Test
    public void deleteFile(){
        Assert.assertTrue(sftpService.deleteFile("/upload/chai/sftp-file/test.xml"));
        Assert.assertTrue(!sftpService.existFile("/upload/chai/sftp-file/test.xml"));
    }

}
