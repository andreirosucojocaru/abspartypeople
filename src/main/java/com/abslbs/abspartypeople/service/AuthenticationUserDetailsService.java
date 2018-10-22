package com.abslbs.abspartypeople.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abslbs.abspartypeople.domain.User;
import com.abslbs.abspartypeople.repository.UserRepository;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
		User user = userRepository.findByCredential(credential);
		if (user != null) {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
			return new org.springframework.security.core.userdetails.User(user.getCredential(), user.getPassword(),
					grantedAuthorities);
		}
		throw new UsernameNotFoundException("User not found");
	}

}
