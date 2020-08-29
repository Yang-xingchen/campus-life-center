package campuslifecenter.usercenter.controller;

import campuslifecenter.common.model.User;
import campuslifecenter.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
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
        return userService.getUser(id);
    }

    @GetMapping("/userList")
    public List<User> userList() {
        return userService.getUserList();
    }

    @PostMapping("/getUsers")
    public List<User> getUsers(@RequestBody List<Long> ids) {
        return userService.getUsers(ids);
    }

}
