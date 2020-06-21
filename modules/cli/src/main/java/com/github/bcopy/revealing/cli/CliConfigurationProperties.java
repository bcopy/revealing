package com.github.bcopy.revealing.cli;

import lombok.Data;

@Data
public class CliConfigurationProperties {
   
   String destination;
   String template;
   
   Boolean prettyPrint;
}
