package com.zh.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zh.pojo.User;
import com.zh.service.IUserService;

@Controller  
@RequestMapping("/user")  
public class UserController {
	@Resource  
    private IUserService userService;  
      
    @RequestMapping("/index")  
    public String toIndex(HttpServletRequest request,Model model){  
        String userId = request.getParameter("id");  
        User user = this.userService.getUserById(userId);  
        model.addAttribute("user", user);  
        return "index";  
    }  
}
