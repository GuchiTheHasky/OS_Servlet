package the.husky.controller;



import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import the.husky.entity.enums.Role;
import the.husky.service.SignificantService;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SystemController {
    private final SignificantService significantService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam(name = "login") String login,
                            @RequestParam(name = "password") String password,
                            HttpSession session) {
        if (login.equals("admin") && password.equals("admin")) {
            session.setAttribute("role", Role.ADMIN);
            log.info("Admin logged in");
            return "redirect:/admin";
        }
        session.setAttribute("role", Role.USER);
        log.info("User logged in");
        return "redirect:/vehicle_all";
    }

    @PostMapping("/logout")
    public String logout() {

        return "redirect:/login";
    }

    @GetMapping("/task")
    public String task() {
        return "task";
    }

    @PostMapping("/task")
    public String taskPost(@RequestParam(name = "answer") String answer,
                           @RequestParam(name = "num1") String num1,
                           @RequestParam(name = "num2") String num2) {
        int expectedAnswer = Integer.parseInt(num1) + Integer.parseInt(num2);
        int actualAnswer = Integer.parseInt(answer);
        if (actualAnswer == expectedAnswer) {
            log.info("Task completed");
            return "redirect:vehicle_all";
        }
        log.info("Task failed");
        return "redirect:/answer";
    }

    @GetMapping("/answer")
    public String taskPost() {
        return "wrong_answer";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        int usersCount = significantService.findAllUsers().size();
        int vehiclesCount = significantService.findAllVehicles().size();
        Map<String, Integer> statistics = Map.of("Users count", usersCount, "Vehicles count", vehiclesCount);
        model.addAttribute("statistics", statistics);
        return "admin";
    }
}
