package com.nt.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.User;
import com.nt.repository.UserRepository;
import com.nt.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
private JwtUtil JwtUtil;
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {

	    User dbuser = userRepo.findByEmail(user.getEmail())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // ✅ correct password check
	    if (!dbuser.getPassword().equals(user.getPassword())) {
	        throw new RuntimeException("Invalid Password");
	    }

	    // ✅ use role from DB (VERY IMPORTANT)
	    return JwtUtil.generateToken(dbuser.getEmail(), dbuser.getRole());
	}
}