package com.bugjc.core;

public class StringUtil {

    public static byte[] hexStringToByteArray(String data) {
        int k = 0;
        byte[] results = new byte[data.length() / 2];
        for (int i = 0; i + 1 < data.length(); i += 2, k++) {
            results[k] = (byte) (Character.digit(data.charAt(i), 16) << 4);
            results[k] += (byte) (Character.digit(data.charAt(i + 1), 16));
        }
        return results;
    }

    public static String fromBytesToHex(byte[] resultBytes){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < resultBytes.length; i++){
            if(Integer.toHexString(0xFF & resultBytes[i]).length() == 1){
                builder.append("0").append(Integer.toHexString(0xFF & resultBytes[i]));
            }else{
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }
}
