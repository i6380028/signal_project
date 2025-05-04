package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
// Changed most of the comments so they suit sentence style.
public class AlertGenerator implements PatientDataGenerator {
    // Change the constand name to UPPER_SNAKE_CASE.
    public static final Random RANDOM_GENERATOR = new Random();
    // Changed the variable name to camelCase. 
    private boolean[] alertStates; // The boolean indicates if the alert is active: if false it means its resolved, if true then it is active. 

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                // There is a 90% chance that the alert will be resolved.
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { 
                    alertStates[patientId] = false;
                    // Output the alert event for a specific patient. 
                    //Used principles of line-wrapping or so called block intendation.
                    outputStrategy.output(
                    patientId, 
                    System.currentTimeMillis(), 
                    "Alert", 
                    "resolved");
                }
            } else {
                // Changed the variable name to camelCase.
                // Average rate (alerts per period), adjust based on desired frequency.
                double lambda = 0.1; 
                // Computes the probability of at least one alert in the period
                double p = -Math.expm1(-lambda); 
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert event for a specific patient.
                    // Used principles of line-wrapping or so called block intendation.
                    outputStrategy.output(
                    patientId, 
                    System.currentTimeMillis(), 
                    "Alert", 
                    "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
