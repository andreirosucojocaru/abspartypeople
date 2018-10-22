package com.abslbs.abspartypeople.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.abslbs.abspartypeople.domain.User;
import com.abslbs.abspartypeople.repository.UserRepository;

@Component
public class AuthenticationAccessSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		User user = userRepository.findByCredential(authentication.getName());
		if (user != null) {
			session.setAttribute("user", user);
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect("/post/all");
	}

}
