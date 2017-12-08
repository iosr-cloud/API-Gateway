package agh.iosr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("video")
public class VideoController {

    @GetMapping
    public ResponseEntity<String> sample(){
        return ResponseEntity.ok("Lul");
    }

}
