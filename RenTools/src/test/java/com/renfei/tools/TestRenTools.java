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
}
