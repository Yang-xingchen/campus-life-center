package campuslifecenter.notice.integration;

import campuslifecenter.common.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-center", path = "/user", contextId = "user")
public interface UserService {

    @GetMapping("/userInfo/{id}")
    User userInfo(@PathVariable("id") Long id);

    @GetMapping("/userList")
    List<User> userList();

    @PostMapping("/getUsers")
    List<User> getUsers(List<Long> ids);
}
