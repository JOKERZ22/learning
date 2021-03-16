package cn.shaonianyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p>ClassName: EurekaApplication</p>
 * <p>Description: eureka</p>
 * <p>Author: ZhangWei</p>
 * <p>Date: 2021-03-16</p>
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

}
