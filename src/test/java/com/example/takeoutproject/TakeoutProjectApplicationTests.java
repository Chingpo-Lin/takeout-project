package com.example.takeoutproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TakeoutProjectApplicationTests {

	@Test
	public void test1() {
		String fileName = "ererdfsf.jpg";
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		System.out.println(suffix);
	}

}
