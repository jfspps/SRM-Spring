package com.srm.model.people;

public class Student extends Person{

    private Guardian guardian1;
    private Guardian guardian2;
    private Teacher personalTutor;

    public Guardian getGuardian1() {
        return guardian1;
    }

    public void setGuardian1(Guardian guardian1) {
        this.guardian1 = guardian1;
    }

    public Guardian getGuardian2() {
        return guardian2;
    }

    public void setGuardian2(Guardian guardian2) {
        this.guardian2 = guardian2;
    }

    public Teacher getPersonalTutor() {
        return personalTutor;
    }

    public void setPersonalTutor(Teacher personalTutor) {
        this.personalTutor = personalTutor;
    }
}
