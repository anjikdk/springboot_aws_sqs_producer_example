package com.example.springboot.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Student {

	private Long id;
	private String name;
	private String rollNumber;
}
