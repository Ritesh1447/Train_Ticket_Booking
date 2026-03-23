package com.nt.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.User;
import com.nt.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userRepo.save(user);
		
	}
	
	@PostMapping("/login/{email}/{password}")
	public String login(@PathVariable String email,@PathVariable String password)
	{
		User user=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
		if(!user.getPassword().equals(password))
		{
			throw new RuntimeException("Invalid Password");
	}
		return "Login Success";
}
}

