package com.leets.blog.entity.enums;

public enum ReportReason {
    SPAM(1), INAPPROPRIATE(2), COPYRIGHT(5);

    private final int weight;

    ReportReason(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}