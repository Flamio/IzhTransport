package com.menshikov.maksim.izhtransport;

import android.app.Application;
import android.test.ApplicationTestCase;


import dalvik.annotation.TestTarget;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application>
{
    public ApplicationTest()
    {
        super(Application.class);
    }



    public void Test1()
    {
        assertEquals(1,5);
    }

}