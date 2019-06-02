package com.renfei.tools;

import org.junit.Test;

/**
 * Created by renfei on 2017/10/26.
 */
public class TestRenTools {


    @Test
    public void SimilarityUtilTest() {

        String str1 = "chenlb.blogjava.net";
        String str2 = "chenlb.javaeye.com";
        System.out.println("ld=" + SimilarityUtil.ld(str1, str2));
        System.out.println("sim=" + SimilarityUtil.sim(str1, str2));
    }


    @Test
    public void testNG(){
        String s = "[[    0.0101,    0.0005,    0.0009,    0.0002,    0.0007,    0.9667,    0.0002,    0.0134,    0.0051,    0.0023]]";

        System.out.println(s.substring(s.lastIndexOf("[") +1,s.indexOf("]")));


    }
}
