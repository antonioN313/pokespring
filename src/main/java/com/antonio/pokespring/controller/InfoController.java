package com.antonio.pokespring.controller;

import com.antonio.pokespring.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {
    
    private final AppConfig.AppInfo appInfo;
    private final String appStartupMessage;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", appInfo.name());
        info.put("version", appInfo.version());
        info.put("description", appInfo.description());
        info.put("startupMessage", appStartupMessage);
        return ResponseEntity.ok(info);
    }
}