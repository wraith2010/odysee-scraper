package com.ten31f.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScrapperTest {

	private static final String TEST_URL01 = "https://odysee.com/@MoviesD0tCom:b";
	private static final String TEST_URL02 = "https://odysee.com/@DeifiedImmortal:0";
	private static final String TEST_URL03 = "https://odysee.com/@DeifiedImmortal:0/Underworld-Movies-Series:1?lid=19f0fe3a6452a7b5f36ad0f84ab0989ba99d86aa";

	private static final String TAG01 = "@MoviesD0tCom:b";
	private static final String TAG02 = "@DeifiedImmortal:0";

	@Test
	public void getUserTagTest() {

		assertEquals(TAG01, new Scrapper(TEST_URL01).getUserTag());
		assertEquals(TAG02, new Scrapper(TEST_URL02).getUserTag());
		assertEquals(TAG02, new Scrapper(TEST_URL03).getUserTag());

	}

}
