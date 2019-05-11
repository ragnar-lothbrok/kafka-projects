package com.test.kafka;

public class Organisation {
    private String organisation_name;
    private String description;
    private int Employees;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Calling getters and setters
    public String getOrganisation_name()
    {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name)
    {
        this.organisation_name = organisation_name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getEmployees()
    {
        return Employees;
    }

    public void setEmployees(int employees)
    {
        Employees = employees;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "organisation_name='" + organisation_name + '\'' +
                ", description='" + description + '\'' +
                ", Employees=" + Employees +
                ", id=" + id +
                '}';
    }
}