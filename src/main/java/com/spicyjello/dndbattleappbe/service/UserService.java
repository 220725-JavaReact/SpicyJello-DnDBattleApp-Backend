package com.spicyjello.dndbattleappbe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spicyjello.dndbattleappbe.data.UserRepository;
import com.spicyjello.dndbattleappbe.model.User;
import com.spicyjello.dndbattleappbe.util.PasswordFactory;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordFactory passwordFactory;
	public User addUser(User newUser) {
		newUser.setPassword(passwordFactory.encodePassword(newUser.getPassword()));
		return userRepo.save(newUser);
	}
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	public Optional<User> getUserById(int id) {
		return userRepo.findById(id);
	}
	public Optional<User> getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	public User updateUser(User updatedUser) {
		Optional<User> foundUser = getUserById(updatedUser.getId());
		if (foundUser.isPresent()) {
			if (!updatedUser.getPassword().equals(foundUser.get().getPassword())) updatedUser.setPassword(passwordFactory.encodePassword(updatedUser.getPassword()));
			return userRepo.save(updatedUser);
		} else {
			User notUpdatedUser = new User();
			notUpdatedUser.setName("User not updated");
			return notUpdatedUser;
		}
	}
	public Boolean deleteUserById(int id) {
		userRepo.deleteById(id);
		return !userRepo.existsById(id);
	}
}
