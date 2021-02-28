package shared.messages;

import shared.model.Pack;

import java.io.Serializable;

public class PublishMessage implements Serializable {
    private final Pack pack;
    private final double rising_step;
    private final double falling_step;
    private final int cooldown;

    public PublishMessage(Pack pack, double rising_step, double falling_step, int cooldown) {
        this.pack = pack;
        this.rising_step = rising_step;
        this.falling_step = falling_step;
        this.cooldown = cooldown;
    }

    public Pack getPack() {
        return pack;
    }

    public double getRisingStep() {
        return rising_step;
    }

    public double getFallingStep() {
        return falling_step;
    }

    public int getCooldown() {
        return cooldown;
    }
}
