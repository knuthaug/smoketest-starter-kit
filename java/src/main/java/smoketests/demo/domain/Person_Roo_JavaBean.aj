// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package smoketests.demo.domain;

import java.lang.String;

privileged aspect Person_Roo_JavaBean {
    
    public String Person.getFirstName() {
        return this.firstName;
    }
    
    public void Person.setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String Person.getLastName() {
        return this.lastName;
    }
    
    public void Person.setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public int Person.getBirthYear() {
        return this.birthYear;
    }
    
    public void Person.setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    
}
