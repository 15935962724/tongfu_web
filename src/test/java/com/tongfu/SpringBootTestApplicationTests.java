package com.tongfu;

import static org.mockito.Matchers.booleanThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SpringBoot单元测试
 *
 * @author wsr
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTestApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
//	Person person;

    @Autowired
    DataSource dataSource;

    @Autowired
    ApplicationContext ioc;

    @Test
    public void testUserService() throws SQLException {
        boolean falg = ioc.containsBean("userService");
        System.err.println(">>>>>>>>>>" + falg);
        System.out.println("lll" + dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println("？？?" + connection);
        connection.close();

    }


    @Test
    public void contextLogs() {

        //日志级别 由低到高trace< debug< info< warn< error
        //可以调整日志级别:就只会
        logger.trace("这是trace日志，跟踪轨迹");
        logger.debug("这是debug日志，调试信息");
        //springboot默认给我们使用的是info级别的,没有指定级别的就用springBoot默认规定的级别;
        logger.info("这是info日志，自定义信息");
        logger.warn("这是warn日志，警告信息");
        logger.error("这是error日志，错误日志");

    }


}
