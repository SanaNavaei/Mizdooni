package mizdooni.controllers;

import mizdooni.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {
    @GetMapping("/status/health")
    public Response health() {
        return Response.ok("server is up", Map.of("condition", "healthy"));
    }
}
