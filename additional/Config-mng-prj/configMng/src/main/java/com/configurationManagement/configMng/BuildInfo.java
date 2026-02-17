package com.configurationManagement.configMng;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="build")
@Data
public class BuildInfo {

    public String id;
    public String version;
    public String name;

}
