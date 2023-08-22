package the.husky.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import the.husky.entity.User;
import the.husky.service.SignificantService;

import java.util.List;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private SignificantService significantService;

    @GetMapping("/user_all")
    public String showAll(Model model) {
        List<User> users = significantService.getUserService().findAll();
        model.addAttribute("users", users);
        return "user_all";
    }

    @GetMapping("/user_add")
    public String addUser() {
        return "user_add";
    }

    @PostMapping("/user_add")
    public String addUserPost(@ModelAttribute User user) {
        significantService.getUserService().add(user);
        log.info("User {} added", user);
        return "redirect:/user_all";
    }

    @GetMapping("/user_edit")
    public String editUser(@RequestParam(name = "id") int userId, Model model) {
        User user = significantService.getUserService().findUserById(userId);
        model.addAttribute("user", user);
        return "user_edit";
    }

    @PostMapping("/user_edit")
    public String editUserPost(@ModelAttribute User user) {
        significantService.getUserService().update(user);
        log.info("User {} updated", user);
        return "redirect:/user_all";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam(name = "id") int userId) {
        significantService.getUserService().delete(userId);
        log.info("User with id {} deleted", userId);
        return "redirect:/user_all";
    }
}
