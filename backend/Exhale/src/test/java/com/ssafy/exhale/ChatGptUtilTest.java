package com.ssafy.exhale;

import com.ssafy.exhale.util.ChatGptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class ChatGptUtilTest {
    @Autowired
    private ChatGptUtil chatGptUtil;

    @Test
    void chatGptUtilTest() {
        chatGptUtil.postRequest("내일 소풍갈래?", "네 내일 소풍가요");
    }
}
