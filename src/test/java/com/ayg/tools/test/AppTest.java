package com.ayg.tools.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    static Logger logger = LoggerFactory.getLogger(AppTest.class);
    
    /**
     *  @Description: 测试
     *  @Author: York.Hwang
     *  @Date: 2020/2/15 23:34
     */
    @Test
    public void testApp()
    {
        sleep("me");
        logger.warn("test OK!");
        assertTrue( true );
    }



    private void sleep(String who){
        logger.info("who"+who);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
}
