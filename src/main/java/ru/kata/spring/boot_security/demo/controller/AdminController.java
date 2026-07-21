package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public String adminPage(Model model) {

        model.addAttribute("users", userService.findAll());

        return "admin";
    }


    @GetMapping("/new")
    public String createUser(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());

        return "create";
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {

        userService.save(user);

        return "redirect:/admin";
    }



    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           Model model) {


        User user = userService.findById(id);


        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());


        return "edit";
    }



    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {


        userService.update(user);


        return "redirect:/admin";
    }



    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Long id,
                             Model model) {


        model.addAttribute(
                "user",
                userService.findById(id)
        );

        return "delete";
    }



    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute User user) {


        userService.deleteById(user.getId());


        return "redirect:/admin";
    }

}