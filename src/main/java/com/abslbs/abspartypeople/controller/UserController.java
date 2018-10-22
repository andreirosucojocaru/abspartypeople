package com.abslbs.abspartypeople.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.abslbs.abspartypeople.domain.User;
import com.abslbs.abspartypeople.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	public final static int DAY_OF_MONTH = 0;
	public final static int MONTH = 1;
	public final static int YEAR = 2;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/all")
	public String allUsers(Model model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		model.addAttribute("user", (User) session.getAttribute("user"));
		model.addAttribute("userList", userRepository.findAll());
		return "allUsers";
	}

	@GetMapping(value = "/displayPhoto")
	public void displayPhoto(@RequestParam("id") Long userId, HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent() && user.get().getPhoto() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(user.get().getPhoto());
			response.getOutputStream().close();
		}
	}

}
