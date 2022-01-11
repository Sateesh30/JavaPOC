package com.neosoft.Poc1.Model;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Users")
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name="name")
	private String name;
	
	@NotNull
	@Column(name="sur_name")
	private String surname;
	
	@NotNull
	@Column(name="pincode")
	private long pincode;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_joining")
	private Date doj;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_birth")
	private Date dob;
	
	@NotNull
	@Column(name="EmailID")
	private String email;
	
	@NotNull
	@Column(name="password")
	private String password ;
	
	@NotNull
	@Column(name="phone_number",unique = true)
	private long phoneno;
	
	@NotNull
	private boolean deleted;
	
	

}


