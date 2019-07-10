package com.shine.iot.signal.db.rest;

import com.shine.iot.signal.db.rest.service.impl.RawTLVSignalServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IotSignalDbRestApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private HikariDataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        System.out.println("使用的连接数据库的数据源为：" + dataSource.getConnection());
        //  HikariProxyConnection@848187627 wrapping com.mysql.cj.jdbc.ConnectionImpl@2fcffc05
    }

    @Autowired
    private RawTLVSignalServiceImpl rawSignalService;

    @Test
    public void testGetAllMsgById() {

    }


}
