package com.kakaopay.throwing.common;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class ThrowUtils {
    public static String makeToken(int length) {
        StringBuilder token = new StringBuilder();
        Random random = new Random();

        while (length-- > 0) {
            int rIndex = random.nextInt(2);
            switch (rIndex) {
                case 0:
                    token.append((char) ((int) (random.nextInt(26)) + 65));
                    break;
                case 1:
                    token.append((char) ((int) (random.nextInt(26)) + 97));
                    break;
            }
        }

        return token.toString();
    }

    public static List<Long> distributeMoney(long money, int count) {
        List<Long> moneyList = Lists.newArrayList();
        long divideMoney = money / count;

        if (money % count != 0) {
            count--;
            moneyList.add(money - divideMoney * count);
        }

        while (count-- > 0) {
            moneyList.add(divideMoney);
        }

        return moneyList;
    }
}
