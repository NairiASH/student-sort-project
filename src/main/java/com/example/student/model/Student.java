package com.example.student.model;

public class Student {
    private final Integer groupNumber;
    private Double scoreAverage;
    private Integer cardNumber;

    public Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.scoreAverage = builder.scoreAverage;
        this.cardNumber = builder.cardNumber;
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

}
