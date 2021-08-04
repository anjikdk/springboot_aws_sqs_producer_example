package com.example.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.resource.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/produce")
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;

	@PostMapping("/message")
	public Student sendMessage(@RequestBody Student student) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(student);
//			queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(jsonString).build());
			queueMessagingTemplate.convertAndSend(endPoint, student);
			logger.info("Message sent successfully  " + jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}
	
	@PostMapping("/messages")
	public List<Student> sendMessages(@RequestBody List<Student> students) {

		try {
			queueMessagingTemplate.convertAndSend(endPoint, students);
			logger.info("Message sent successfully  " + students);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}
}