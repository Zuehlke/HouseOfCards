package com.zuehlke.houseofcards;

public class PlayerInfo {
    private String name;
    private long chipstack;

    public PlayerInfo(String name, long chipstack) {
        this.name = name;
        this.chipstack = chipstack;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "name='" + name + '\'' +
                ", chipstack=" + chipstack +
                '}';
    }
}
