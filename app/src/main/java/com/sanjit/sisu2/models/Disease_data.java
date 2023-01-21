package com.sanjit.sisu2.models;

public class Disease_data {

    private String name;
    private String description;
    private String symptoms;
    private String treatment;
    private String prevention;
    private String vaccine;
    private String vaccine_description;
    private String vaccine_precautions;
    private String vaccine_side_effects;
    private String vaccine_dosage;
    private String vaccine_frequency;

    public Disease_data(String name, String description, String symptoms, String treatment, String prevention, String vaccine, String vaccine_description, String vaccine_precautions, String vaccine_side_effects, String vaccine_dosage, String vaccine_frequency) {
        this.name = name;
        this.description = description;
        this.symptoms = symptoms;
        this.treatment = treatment;
        this.prevention = prevention;
        this.vaccine = vaccine;
        this.vaccine_description = vaccine_description;
        this.vaccine_precautions = vaccine_precautions;
        this.vaccine_side_effects = vaccine_side_effects;
        this.vaccine_dosage = vaccine_dosage;
        this.vaccine_frequency = vaccine_frequency;
    }
}
