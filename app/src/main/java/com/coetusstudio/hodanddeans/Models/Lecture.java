package com.coetusstudio.hodanddeans.Models;

public class Lecture {

    String lectureName, lectureLink,lectureTiming, lectureDate, lectureTime;

    public Lecture(String lectureName, String lectureLink, String lectureTiming, String lectureDate, String lectureTime) {
        this.lectureName = lectureName;
        this.lectureLink = lectureLink;
        this.lectureTiming = lectureTiming;
        this.lectureDate = lectureDate;
        this.lectureTime = lectureTime;
    }

    public Lecture() {
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureLink() {
        return lectureLink;
    }

    public void setLectureLink(String lectureLink) {
        this.lectureLink = lectureLink;
    }

    public String getLectureTiming() {
        return lectureTiming;
    }

    public void setLectureTiming(String lectureTiming) {
        this.lectureTiming = lectureTiming;
    }

    public String getLectureDate() {
        return lectureDate;
    }

    public void setLectureDate(String lectureDate) {
        this.lectureDate = lectureDate;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }
}
