package com.zh.controller;

import java.util.ArrayList;
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zh.pojo.User; 
 
@Controller
@RequestMapping(value="lr")
public class LoginController {
	//��̬List<User>���ϣ��˴��������ݿ���������ע����û���Ϣ
	private static List<User> userList;
	//UserController��Ĺ���������ʼ��List<User>����
	public LoginController(){
		super();
		userList = new ArrayList<User>();
	}
	//��̬��֮��loggerMaggent
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String registerForm(){
		logger.info("register GET ���������á���");
		return "register";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(@RequestParam("loginname")String loginname,
			@RequestParam("password")String password,
			@RequestParam("username")String username){
		logger.info("register POST ���������á���");
		//����user����
		User user = new User();
		user.setLoginname(loginname);
		user.setPassword(password);
		user.setUsername(username);
		//ģ�����ݿ�洢User��Ϣ
		userList.add(user);
		return "login";		
	}
	
	//��¼
	@RequestMapping("/login")
	public String login(@RequestParam("loginname")String loginname,
			@RequestParam("password")String password,Model model){
		logger.info("��¼����"+loginname+" ���룺"+password);
		//�������в����û��Ƿ���ڣ��˴�����ģ�����ݿ���֤
		for(User user:userList){
			if(user.getLoginname().equals(loginname) && user.getPassword().equals(password)){
				model.addAttribute("user", user);
				return "welcome";
			}
		}
		return "login";
	}
	
}

