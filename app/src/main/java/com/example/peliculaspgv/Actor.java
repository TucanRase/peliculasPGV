package com.example.peliculaspgv;

public class Actor {
    private String original_name;
    private String profile_path;
    private String character;

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Actor(String original_name, String profile_path, String character) {
        this.original_name = original_name;
        this.profile_path = profile_path;
        this.character = character;
    }
}
