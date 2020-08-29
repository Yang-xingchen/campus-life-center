package campuslifecenter.usercenter.controller;

import campuslifecenter.common.model.User;
import campuslifecenter.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/singUp")
    public boolean singUp(User user) {
        try {
            return userService.singUp(user);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/singIn")
    public boolean singIn(User user) {
        return userService.singIn(user);
    }

    @GetMapping("/userInfo/{id}")
    public User userInfo(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        user.setPassword(null);
        return user;
    }

    @GetMapping("/userList")
    public List<User> userList() {
        return userService
                .getUserList()
                .stream()
                .peek(user -> user.setPassword(null))
                .collect(Collectors.toList());
    }

    @PostMapping("/getUsers")
    public List<User> getUsers(@RequestBody List<Long> ids) {
        return userService
                .getUsers(ids)
                .stream()
                .peek(user -> user.setPassword(null))
                .collect(Collectors.toList());
    }

}
