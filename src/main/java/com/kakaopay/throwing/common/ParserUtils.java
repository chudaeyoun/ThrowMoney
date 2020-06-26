package com.kakaopay.throwing.common;

import javax.servlet.http.HttpServletRequest;

public class ParserUtils {
    private static final String userHeader = "X-USER-ID";
    private static final String roomHeader = "X-ROOM-ID";

    public static int getUserHeader(HttpServletRequest request) {
        return Integer.parseInt(request.getHeader(userHeader));
    }

    public static String getRoomHeader(HttpServletRequest request) {
        return request.getHeader(roomHeader);
    }
}
