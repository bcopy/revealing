package com.github.bcopy.revealing.cli;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CliConfigurationProperties {
   List<String> inputs = new ArrayList<>();
   
   String template;
   
   Boolean prettyPrint;
}
