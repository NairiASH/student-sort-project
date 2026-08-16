package com.example.student.model;

import java.util.Objects;

public class Student {
    private final Integer groupNumber;
    private Double scoreAverage;
    private Integer cardNumber;

    public Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.scoreAverage = builder.scoreAverage;
        this.cardNumber = builder.cardNumber;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public Double getScoreAverage() {
        return scoreAverage;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return groupNumber + " " + scoreAverage + " " + cardNumber;
    }

    public static class Builder {
        private final Integer groupNumber;
        private Double scoreAverage;
        private Integer cardNumber;

        public Builder(Integer groupNumber) {
            this.groupNumber = groupNumber;
        }

        public Builder scoreAverage(Double scoreAverage) {
            this.scoreAverage = scoreAverage;
            return this;
        }

        public Builder cardNumber(Integer cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Student otherStudent)) {
            return false;
        }

        return Objects.equals(groupNumber, otherStudent.groupNumber)
                && Double.compare(scoreAverage, otherStudent.scoreAverage) == 0
                && Objects.equals(cardNumber, otherStudent.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, scoreAverage, cardNumber);
    }
}
