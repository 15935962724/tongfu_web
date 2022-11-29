package com.tongfu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

//@ImportResource(locations= {"classpath:beans.xml"})//导入指定的配置文件
@MapperScan(value = "com.tongfu.dao")
@SpringBootApplication
//@ComponentScan(basePackages={"com.tongfu"})
public class SpringBootTestApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SpringBootTestApplication.class, args);
        System.err.println("tongfu-web启动完毕");

        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("Local HostAddress: "+addr.getHostAddress());
            //Runtime.getRuntime().exec("cmd   /c   start   http://localhost:8082/tongfu");//可以指定自己的路径
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
