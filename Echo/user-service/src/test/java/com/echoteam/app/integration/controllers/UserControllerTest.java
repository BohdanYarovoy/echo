package com.echoteam.app.integration.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {



}