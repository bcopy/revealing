package com.github.bcopy.revealing.process;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProcessorConfigurationProperties {
  private List<String> visitors = new ArrayList<>();
  
  private List<String> contentPaths = new ArrayList<>();
}
