package com.nt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Users")
@Getter
@Setter
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
private Integer id;
private String name;
private String email;
private String password;
private String role;
}
