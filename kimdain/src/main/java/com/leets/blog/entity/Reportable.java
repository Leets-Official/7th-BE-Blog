package com.leets.blog.entity;

import com.leets.blog.entity.enums.ContentStatus;

public interface Reportable {
    void addWeight(int weight);
    int getTotalWeight();
    void updateStatus(ContentStatus status);
}