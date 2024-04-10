package com.sparta.smern.records;

import java.util.List;

public record Pet(int id, Category category, String name, List<String> photoUrls, List<Tag> tags, String status) {

    public record Category(int id, String name) {}

    public record Tag(int id, String name) {}
}
