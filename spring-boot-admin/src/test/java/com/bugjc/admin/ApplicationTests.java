package com.bugjc.admin;

import com.bugjc.admin.config.DruidDBConfig;
import com.bugjc.admin.util.SHA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	 @Autowired
	 private DruidDBConfig druidDBConfig;


    @Test
    public void testDruidDBConfig() throws Exception{
        System.out.println("DruidDBConfig = " + druidDBConfig.dataSource().getLoginTimeout());
    }

    @Test
    public void test() throws Exception {
		String pwd = SHA.encryptSHA("123456");
        System.out.println("---------");
		System.out.println(pwd);
    }

}
