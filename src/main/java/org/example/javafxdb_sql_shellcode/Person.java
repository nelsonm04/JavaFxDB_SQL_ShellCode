package org.example.javafxdb_sql_shellcode;

public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private String dept;
    private String major;

    public Person() {}

    public Person(Integer id, String firstName, String lastName, String dept, String major) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dept = dept;
        this.major = major;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

}
