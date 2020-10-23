package com.github.bcopy.revealing.generate;

import java.nio.charset.StandardCharsets;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerationConfigurationProperties {

	String template;
   
   @Builder.Default
   String templatePath = "templates/";
   
   @Builder.Default
   TemplateLocatorEnum locator = TemplateLocatorEnum.CLASSPATH;
   
   @Builder.Default
   String destination= "output/";
   
   @Builder.Default
   Boolean prettyPrint = false;
   
   @Builder.Default
   String encoding = StandardCharsets.UTF_8.toString();
}
