package com.nikos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// This is an example of Type-safe Configuration Properties
//
// it requires @Configuration and @EnableConfigurationProperties(Foo.class) if placed on another class
// Or just a @Configuration if placed here
@Configuration
// "test" is the root element in YML file
@ConfigurationProperties(prefix = "test")
public class TypeSafeConfiguration {

	static String foo;

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		TypeSafeConfiguration.foo = foo;
	}

}
