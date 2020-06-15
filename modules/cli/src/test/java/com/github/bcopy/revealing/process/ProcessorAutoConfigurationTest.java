package com.github.bcopy.revealing.process;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.bcopy.revealing.cli.Main;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
class ProcessorAutoConfigurationTest {
	
	@Autowired
	ApplicationContext applicationContext;

	@Test
	@DirtiesContext
	void testDefaultVisitors() {
		assertTrue(applicationContext.containsBean("exifMetadataVisitor"));
	}
	
}
