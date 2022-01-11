package com.neosoft.Poc1;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;

import com.neosoft.Poc1.Controller.UserContoller;
import com.neosoft.Poc1.Model.User;
import com.neosoft.Poc1.Repository.UsersRepository;
import com.neosoft.Poc1.Service.InvalidRequestException;
import com.neosoft.Poc1.Service.UserService;

@WebMvcTest(UserContoller.class)
public class Poc1ApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;
	// ObjectMapper provides functionality for reading and writing JSON,
	// either to and from basic POJOs

	@MockBean
	UserService userService;

	@MockBean
	UsersRepository usersRepository;

	User u1 = new User(1, "Rahul", "Dravid", (long) 401209, new Date(2021 - 8 - 11), new Date(2003 - 12 - 10),
			"rahul@gmail.com", "rahul", (long) 832972048,false);
	User u2 = new User(2, "Kannur", "Rahul", (long) 401209, new Date(2021 - 8 - 11), new Date(2000 - 11 - 23),
			"Rahul32@gmail.com", "Kannur", (long) 832972048,false);
	User u3 = new User(3, "Mark", "Henry", (long) 401209, new Date(2022 - 8 - 11), new Date(2021 - 11 - 23),
			"mark34@gmail.com", "mark", (long) 832972048, false);

	@Test
	void contextLoads() {
	}

	@Test
	public void getAllRecords_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(u1, u2,u3));

		Mockito.when(userService.getAllUsers()).thenReturn(records);
		// When findAll called then ready with records (No DB calls)
		mockMvc.perform(MockMvcRequestBuilders.get("/allusers").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()) // 200
				.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[2].name", is("Mark")));
	}
	
	
	@Test
	public void createRecord_success() throws Exception {
		User record = User.builder().id(5).name("Alex").surname("Scott").pincode((long) 401280)
				.doj(new Date(2021 - 12 - 11)).dob(new Date(1998 - 8 - 15)).email("alex@gmail.com").password("alex123")
				.phoneno((long) 80804454).build();

		Mockito.when(userService.addUsers(record)).thenReturn(record);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/add/users")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(record));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Alex")));
	}

	@Test
	public void deleteUsersById_success() throws Exception {

		Mockito.when(userService.getUsersById(u2.getId())).thenReturn(Optional.of(u2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/delete/users/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	


	
	

	@Test
	public void getUserByPincode_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(u1, u2,u3));
		Mockito.when(userService.getUsersByPincode(u1.getPincode())).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/usersbypincode/401209").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].pincode", is(401209)));
	}

	@Test
	public void getUsersByName_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(u1, u2,u3));
		Mockito.when(userService.getUsersByName(u1.getName())).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/usersbyname/Kajal").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].name", is("Kajal")));
	}

	@Test
	public void getUsersBySurname_success() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(u1, u2,u3));
		Mockito.when(userService.getUsersBySurName(u1.getSurname())).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/usersbysurname/Scott").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].surname", is("Scott")));
	}

	@Test
	public void getUsersBySortByDoj() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(u1, u2,u3));
		Mockito.when(userService.sortByDoj()).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/sort/sortbyDoj").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[0].name", is("Alex")));
	}

	@Test
	public void getUsersBySortByDob() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(u1, u2,u3));
		Mockito.when(userService.sortByDob()).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/sort/sortbyDob").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$[2].id", is(3)));

	}

	@Test
	public void updateUsersRecord_success() throws Exception {
		User record = User.builder().id(1).name("Kannur").surname("Rahul").pincode((long) 401280)
				.doj(new Date(2021 - 12 - 11)).dob(new Date(1998 - 8 - 15)).email("kannurLokesh12@gmail.com").password("rahul12")
				.phoneno((long) 80804454).build();

		Mockito.when(userService.getUsersById(u2.getId())).thenReturn(Optional.of(u2));

		Mockito.when(userService.update(record)).thenReturn(record);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/update/users/2")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(record));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Kannur")));
	}


}
