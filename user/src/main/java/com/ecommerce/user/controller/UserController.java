package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRepsonse;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

//    public List<User> userList = new ArrayList<>();

    private final UserService userService;
//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /* if you comment to logger in use lombok @Slf4j then you have to use log instead logger only */

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/users")
//    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserRepsonse>> getAllUsers() {
        List<UserRepsonse> allUsers = userService.fetchAllUser();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<String> CreateUser(@RequestBody UserRequest userRequest) {

        userService.createUser(userRequest);
        return new ResponseEntity<>("User is saved", HttpStatus.OK);
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserRepsonse> fetchUser(@PathVariable String id) {

        log.info("Request received for user: {}", id);

        log.trace("This is TRACE level- very detailed logs");
        log.debug("This is DEBUG level- Used for development debugging");
        log.info("This is INFO level- General system information");
        log.warn("This is WARN level- something might be wrong");
        log.error("This is ERROR level- something failed");

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->  ResponseEntity.notFound().build());
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest UpdatedUserRequest) {

        boolean updated = userService.UpdateUser(id, UpdatedUserRequest);
        if(updated)
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
    }

}
