package entities;

import entities.internal.Vector;
import settings.Constants;

import java.util.Arrays;

public class Person {

    private static final int REGULAR = 0;
    private static final int INFECTED = 1;
    private static final int HEALED = 2;
    private static final int DEAD = 3;

    // --- --- --- --- --- --- --- --- --- --- --- --- //

    private Vector position;
    private Vector velocity;

    private boolean changed = false;
    private int lifeTime = Constants.INFECTION_TIME;
    private int state = 0;

    public Person(Vector position, Vector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void update(double percentInfected) {
        if(isInfected()) {
            if(--lifeTime <= 0) {
                state = percentInfected > Constants.HEALTH_INDUSTRY || Math.random() > Constants.HEAL_CHANCE
                        ? DEAD : HEALED;
            }
        }

        position.add(velocity);
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isInfected() {
        return state == INFECTED;
    }

    public void setInfected() {
        if(!isHealed()) {
            state = INFECTED;
        }
    }

    public boolean isHealed() {
        return state == HEALED;
    }

    public void setHealed() {
        state = HEALED;
    }

    public boolean isDead() {
        return state == DEAD;
    }

    public void setDead() {
        state = DEAD;
    }
}
