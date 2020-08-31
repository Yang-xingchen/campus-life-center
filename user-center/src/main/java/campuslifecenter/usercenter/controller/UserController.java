package campuslifecenter.usercenter.controller;

import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.UserInfo;
import campuslifecenter.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/signUp")
    public boolean signUp(User user) {
        try {
            return userService.signUp(user);
        } catch (Exception e) {
            log.error("sign up error!", e);
            return false;
        }
    }

    @PostMapping("/signIn")
    public boolean signIn(User user) {
        return userService.signIn(user);
    }

    @GetMapping("/userInfo/{id}")
    public UserInfo userInfo(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/userList")
    public List<UserInfo> userList() {
        return userService.getUserList();
    }

    @PostMapping("/getUsers")
    public List<UserInfo> getUsers(@RequestBody List<Long> ids) {
        return userService.getUsers(ids);
    }

}
