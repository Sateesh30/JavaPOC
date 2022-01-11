package com.neosoft.Poc1.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.neosoft.Poc1.Model.User;
import com.neosoft.Poc1.Repository.UsersRepository;

@Component
public class UserService {
	
	@Autowired
	private UsersRepository userRepo;
	
	
	public List<User> getSoftUsers(){
		return userRepo.findAllDeletedFalse();
	}
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	public User addUsers(User user) {
		return userRepo.save(user);
	}

	public void deleteUsersById(int id) {
		userRepo.deleteById(id);
		
	}

	public Optional<User> getUsersById(int l) {
		
		return userRepo.findById(l);
	}

	public User update(User user) {
		
		return userRepo.save(user);
	}

	public List<User> getUsersByName(String name) {
		
		return userRepo.findByName(name);
	}

	public List<User> getUsersBySurName(String surname) {
		
		return userRepo.findBySurname(surname);
	}

	public List<User> getUsersByPincode(long pincode) {
		
		return userRepo.findByPincode(pincode);
	}
	
	
	public List<User> sortByDoj() {
		
	 return userRepo.findAll().stream().sorted((o1, o2) -> o1.getDoj().
				compareTo(o2.getDoj())).collect(Collectors.toList());
		
	}
	
	public List<User> sortByDob() {
			Comparator<User> byDobComparator = Comparator.comparing(User::getDob);
			return  userRepo.findAll().stream().sorted(byDobComparator).collect(Collectors.toList());
		}

	public void softdeleteUsersById(int id,boolean value) {
		userRepo.softDelete(id,value);
	}

	

}
