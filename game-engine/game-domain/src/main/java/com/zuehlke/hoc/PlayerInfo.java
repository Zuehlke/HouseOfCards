package com.zuehlke.hoc;

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

    public String getName() {
        return name;
    }

    public long getChipstack() {
        return chipstack;
    }

}
