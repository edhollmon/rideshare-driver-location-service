package com.rideshare.driver_location_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@RestController
public class DriverLocationController {

    @Value("${redis.url}")
    private String redisUrl;

    @Value("${redis.port}")
    private int redisPort;

    @PostMapping("/driver-location")
    public String addDriverLocation(@RequestParam String driverId, @RequestParam double latitude, @RequestParam double longitude) {
        try (Jedis jedis = new Jedis(redisUrl, redisPort)) {
            jedis.geoadd("drivers:locations", longitude, latitude, driverId);
            return "Driver location added successfully.";
        } catch (Exception e) {
            return "Error adding driver location: " + e.getMessage();
        }
    }
}
