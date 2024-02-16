package com.ssafy.exhale.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

public class GenerateRandomKey{

    public static String getRandomKey(Long memberId) {
        int length = 6;
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        Random random = new SecureRandom();
        StringBuilder key = new StringBuilder();
        key.append(LocalDateTime.now()).append(memberId);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            key.append(CHARACTERS.charAt(randomIndex));
        }

        return key.toString();
    }
}
