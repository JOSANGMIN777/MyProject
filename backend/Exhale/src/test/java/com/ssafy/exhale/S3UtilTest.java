package com.ssafy.exhale;

import com.ssafy.exhale.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
public class S3UtilTest {

    @Autowired
    private S3Util s3Util;

    @Test
    void saveImageTest() throws IOException {
        String fullPath = "C:/project/";
        String fileName = "test.txt";
        File file = new File(String.format(fullPath + "%s", fileName));

        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file.txt",
                file.getName(), "application/octet-stream", input);

        String url = s3Util.saveImage(multipartFile);
        System.out.println(url);
    }

    @Test
    void deleteImageTest() {
        s3Util.deleteImage("960bf29f-e5de-416a-8e29-b2f82923e39a.txt");
    }
}
