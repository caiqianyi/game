package com.lebaoxun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.caiqianyi.soa.web.framework.datasource.DynamicDataSourceRegister;

/**
 *
 */
@Import({DynamicDataSourceRegister.class})
@SpringBootApplication
@EnableTransactionManagement
public class GameServerApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(GameServerApplication.class, args);
    }
}
