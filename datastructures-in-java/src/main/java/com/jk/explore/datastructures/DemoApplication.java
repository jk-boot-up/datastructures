package com.jk.explore.datastructures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.PriorityQueue;

@SpringBootApplication
public class DemoApplication {



	public static void main(String[] args) {
		PriorityQueue<Integer> a = new PriorityQueue<>();
		SpringApplication.run(DemoApplication.class, args);

	}

}
