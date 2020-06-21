package com.github.bcopy.revealing.cli.generate;

import java.nio.charset.StandardCharsets;

import lombok.Data;

@Data
public class GenerationConfigurationProperties {
   
   String template;
   
   String templatePath;
   
   Boolean prettyPrint;
   
   String encoding = StandardCharsets.UTF_8.toString();
}
