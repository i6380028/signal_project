package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
// Changed all the comments so they suit sentence style.
// Changed position of brackets so they follow Nonempty blocks: K & R style.
public class FileOutputStrategy implements OutputStrategy {
    // Changed variable name to camelCase. 
    private String baseDirectory;
    // Changed variable name to camelCase.
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // If there is no directory, create it. 
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // If file path does not yet exist compute it and save for the given label.
       // Changed variable name to camelCase.
        String filePath = fileMap.computeIfAbsent(
            label,
         k -> Paths.get(baseDirectory, label + ".txt").toString()
        );
 
         //Used principles of line-wrapping or so called block intendation.
        // Write the data of a patient to the file, it might be creating it or adding information.
       
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath),
                StandardOpenOption.CREATE,
                 StandardOpenOption.APPEND
                 )
              )
          ) {
           // Used principles of line-wrapping.
                    out.printf(
                "Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", 
                patientId, timestamp, label, data
                );
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}