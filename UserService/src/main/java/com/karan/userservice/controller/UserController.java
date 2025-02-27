package com.karan.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karan.userservice.Dto.UserInfoDTO;
import com.karan.userservice.service.UserService;

@RestController
@RequestMapping("/user/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDTO> getUser(
            @RequestBody UserInfoDTO user
    ){
        UserInfoDTO userInfoDTO = userService.getUser(user);
        return ResponseEntity.ok(userInfoDTO);
    }

    @PostMapping("/update")
    public void createUpdate(){

    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }

    @GetMapping("/health")
    public ResponseEntity<Boolean> checkHealth(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @KafkaListener(
            topics = "${spring.kafka.topic-json.name}" ,
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(UserInfoDTO event){
        System.out.println("Received event: --> " + event.toString());
        userService.saveUser(event);
    }

}
