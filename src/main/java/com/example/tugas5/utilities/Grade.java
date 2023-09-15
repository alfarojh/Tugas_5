package com.example.tugas5.utilities;

public enum Grade {
    A,
    B,
    C,
    D,
    E,
    F,
    K;

    public static Grade getGradeForScore(Integer score) {
        if (score == null) {
            return K;
        } else if (score > 90 && score <= 100) {
            return A;
        } else if (score > 80 && score <= 90) {
            return B;
        } else if (score > 70 && score <= 80) {
            return C;
        } else if (score > 60 && score <= 70) {
            return D;
        } else if (score > 50 && score <= 60) {
            return E;
        } else {
            return F;
        }
    }
}
