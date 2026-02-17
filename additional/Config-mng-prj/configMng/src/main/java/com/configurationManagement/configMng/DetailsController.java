package com.configurationManagement.configMng;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RefreshScope
public class DetailsController {

//    @Value("${build.id:default}")
//    public String id;
//
//    @Value("${build.version:default}")
//    public String version;
//
//    @Value("${build.name:default}")
//    public String name;

    private BuildInfo buildInfo;

    @GetMapping("/info")
    public String details() {
        return "Build id: " + buildInfo.id + ", Version: " + buildInfo.version + ", Name: " + buildInfo.name;
    }



}
