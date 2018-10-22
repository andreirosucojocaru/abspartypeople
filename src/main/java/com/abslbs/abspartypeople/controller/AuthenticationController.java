package com.abslbs.abspartypeople.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.abslbs.abspartypeople.domain.Role;
import com.abslbs.abspartypeople.domain.User;
import com.abslbs.abspartypeople.repository.RoleRepository;
import com.abslbs.abspartypeople.repository.UserRepository;

@Controller
@RequestMapping("/")
public class AuthenticationController {
	
	public final static int DAY_OF_MONTH = 0;
	public final static int MONTH = 1;
	public final static int YEAR = 2;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"/", "/login"})
    public String login(Model model) {
    	model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/accessDenied";
    }
    
	@PostMapping("/add")
	public RedirectView addUser(@ModelAttribute User user, @RequestParam("profilePhoto") MultipartFile profilePhoto,
			@RequestParam("birthDate") String birthDate, @RequestParam("roleName") String roleName) throws IOException {
		String[] dateOfBirthParts = birthDate.split("[/]");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setDateOfBirth(Date.valueOf(LocalDate.of(Integer.parseInt(dateOfBirthParts[YEAR]),
				Integer.parseInt(dateOfBirthParts[MONTH]), Integer.parseInt(dateOfBirthParts[DAY_OF_MONTH]))));
		Role role = roleRepository.findByName(roleName);
		if (role != null) {
			user.setRole(role);
		}
		user.setPhoto(profilePhoto.getBytes());
		userRepository.save(user);
		return new RedirectView("login");
	}

}
