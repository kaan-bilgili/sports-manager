package com.sportsmanager.core.model;

import java.util.UUID;

public abstract class AbstractCoach {
    private final UUID id;
    private String name;
    private int age;
    private String specialization;
    private int experience;

    public AbstractCoach(String name, int age, String specialization, int experience) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
        this.specialization = specialization;
        this.experience = experience;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String spec) { this.specialization = spec; }
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
}
