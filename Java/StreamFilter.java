// Day 1: Filtering collections efficiently using Java 8 Streams
// Topic: Functional Programming in Core Java

import java.util.*;
import java.util.stream.*;

public class StreamFilter {
    public static void main(String[] args) {
        List<String> technologies = Arrays.asList("Java", "React", "Oracle SQL", "Vue", "Spring Boot");

        // Extracting only backend-focused skills
        List<String> backendStack = technologies.stream()
            .filter(tech -> tech.contains("Java") || tech.contains("Spring") || tech.contains("SQL"))
            .collect(Collectors.toList());

        System.out.println("Backend Tech Stack: " + backendStack);
    }
}
