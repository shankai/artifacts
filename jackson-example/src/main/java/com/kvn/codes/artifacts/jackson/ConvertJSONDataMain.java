package com.kvn.codes.artifacts.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConvertJSONDataMain {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream = ConvertJSONDataMain.class.getClassLoader().getResourceAsStream("5Gbasestation.json");
        JsonNode jsonNode = mapper.readTree(stream);

        ArrayNode arrayNode = mapper.createArrayNode();
        int size = jsonNode.size();
        for (int i = 0; i < size; i++) {
            JsonNode node = jsonNode.get(i);
            ObjectNode on = mapper.createObjectNode();

            JsonNode bsName = node.get("name");
            on.set("name", bsName);
            on.set("address", bsName);

            JsonNode lookAt = node.get("LookAt");

            on.putObject("wgsLocation").put("lat", lookAt.get("latitude").asDouble()).put("lon", lookAt.get("longitude").asDouble());

            on.setAll((ObjectNode) lookAt);
            on.remove("latitude");
            on.remove("longitude");

            on.put("definitionId", "${ID}");
            on.put("version", "${VERSION}");
            on.put("tenant", "${TENANT}");

            String idResult = md5s(bsName.asText());
            //
            on.put("id", idResult);
            on.put("thing", idResult);
            on.put("statusExpDesc", "");
            // system
            on.put("category", "infrastructure");
            on.put("type", "5Gbasestation");
            on.put("status", "Online");
            on.put("manufacturer", "CMCC");
            on.put("thingTenant", "CMCC");

            arrayNode.add(on);
        }
        System.out.println(arrayNode.toPrettyString());

    }

    public static String md5s(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            String str = buf.toString();
//            System.out.println("result: " + buf.toString());// 32位的加密
//            System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
            return str;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
