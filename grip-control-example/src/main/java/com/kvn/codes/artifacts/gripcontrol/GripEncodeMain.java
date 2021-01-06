package com.kvn.codes.artifacts.gripcontrol;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GripEncodeMain {
    public static void main2(String[] args) {
        System.out.println(11);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.getProperty("file.encoding");
        System.out.println(Charset.defaultCharset().name());

        System.out.println("中文".length());
        System.out.println("中文".getBytes().length);
        System.out.println("中文".getBytes("UTF-8").length);
        System.out.println("中文".getBytes("UTF-16").length);

        String s = new String("中文".getBytes(), StandardCharsets.US_ASCII);
        System.out.println(s);
        System.out.println("ISO-8859-1" + s.length());

        String s2 = new String("中文".getBytes(), StandardCharsets.US_ASCII);
        System.out.println(s2);
        System.out.println("US_ASCII" + s2.length());

        String body = "TEXT d\r\n" +
                "中文1234中\r\n";
        System.out.println("\r\n.len: " + "\r\n".length());
        System.out.println("body.len:" + body.length());

        body = new String(body.getBytes("UTF-8"), StandardCharsets.ISO_8859_1);

        int start = 0;
        while (start < body.length()) {

            int at = body.indexOf("\r\n", start);
            System.out.println("at: " + at);
//            if (at == -1)
//                throw new IllegalArgumentException("bad format");
            String typeline = body.substring(start, at);
            start = at + 2;
            System.out.println("start: " + start);
            System.out.println("typeline:" + typeline);
            at = typeline.indexOf(" ");
            if (at >= 0) {
                String etype = typeline.substring(0, at);
                System.out.println("typeline:" + typeline);
                System.out.println("etype:" + etype);
//                System.out.println("typeline.sub: " + typeline.substring(at + 1, typeline.getBytes().length));
//                System.out.println("clen:" + Integer.parseInt(typeline.substring(at + 1, typeline.length()), 16));
                int clen = Integer.parseInt(typeline.substring(at + 1, typeline.length()), 16);
                String content = body.substring(start, start + clen);
                start += clen + 2;
                System.out.println(new String(content.getBytes("ISO-8859-1"), StandardCharsets.UTF_8));
                System.out.println("etype---content:" + etype + "---" + content);
            } else {
                System.out.println("typeline:" + typeline);
            }
        }
    }
}
