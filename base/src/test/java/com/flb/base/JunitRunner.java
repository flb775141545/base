package com.flb.base;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 打包测试
 * 
 * @author wuxiaoping
 *
 */
@RunWith(Suite.class)
@SuiteClasses({TestUnit.class,TestParameters.class})
public class JunitRunner
{
}
