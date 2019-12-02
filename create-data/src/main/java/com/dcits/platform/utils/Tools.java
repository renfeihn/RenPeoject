package com.dcits.platform.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tools {
    static Map<String, Long> seqMap = new HashMap();

    public Tools() {
    }

    public static String pd(Object o, String lr, String pd, int length) {
        String str = "";
        if(o != null) {
            str = o.toString();
        }

        if(pd.length() != 1) {
            throw new RuntimeException("补位字符只能为一个字符");
        } else {
            int strLength = str.length();
            if(strLength > length) {
                throw new RuntimeException("字符串长度超过指定长度");
            } else if(length == strLength) {
                return str;
            } else {
                int pdCount = length - strLength;
                StringBuffer sb = new StringBuffer();
                if("R".equals(lr)) {
                    sb.append(str);
                }

                for(int i = 0; i < pdCount; ++i) {
                    sb.append(pd);
                }

                if("L".equals(lr)) {
                    sb.append(str);
                }

                return sb.toString();
            }
        }
    }

    public static Date Str2DateTime(Object o) {
        if(o != null) {
            Date d = null;
            String strDate = o.toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                d = sdf.parse(strDate);
            } catch (ParseException var5) {
                ;
            }

            return d;
        } else {
            return null;
        }
    }

    public static String BaseAcctNoEncrypt(Object o) {
        String str = "";
        if(o != null) {
            str = o.toString() + "ifp-md5";
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

            try {
                byte[] e = str.getBytes();
                MessageDigest mdigest = MessageDigest.getInstance("MD5");
                mdigest.update(e);
                byte[] md = mdigest.digest();
                int len = md.length;
                char[] s = new char[len * 2];
                int k = 0;

                for(int i = 0; i < len; ++i) {
                    byte byte0 = md[i];
                    s[k++] = hexDigits[byte0 >>> 4 & 15];
                    s[k++] = hexDigits[byte0 & 15];
                }

                return new String(s);
            } catch (NoSuchAlgorithmException var11) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static int rand(int seed) {
        Random random = new Random();
        return random.nextInt(seed);
    }

    public static synchronized long getSeqNo(String seqName) {
        Long seqNo = (Long)seqMap.get(seqName);
        if(seqNo == null) {
            seqNo = Long.valueOf(0L);
        }

        seqNo = Long.valueOf(seqNo.longValue() + 1L);
        seqMap.put(seqName, seqNo);
        return seqNo.longValue();
    }

    public static void setInitValue(String seqName, long intValue) {
        seqMap.put(seqName, Long.valueOf(intValue));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(pd("20000000", "L", "0", 7));
    }
}
