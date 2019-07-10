package com.shine.iot.signal.db.rest;

import com.shine.iot.signal.db.rest.dao.DeviceAllMsgDao;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDaoAR {
    @Autowired
    private HikariDataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        System.out.println("使用的连接数据库的数据源为：" + dataSource.getConnection());
        //HikariProxyConnection@1743224658 wrapping com.mysql.cj.jdbc.ConnectionImpl@7ead1d80
    }

    @Autowired
    private DeviceAllMsgDao deviceAllMsgDao;

    @Test
    public void testGetAllMsgById() {
        System.out.println("根据allmsg表的ID获取表记录：" + deviceAllMsgDao.selectById(1));
    }

}
