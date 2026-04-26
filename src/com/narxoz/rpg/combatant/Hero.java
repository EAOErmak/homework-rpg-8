package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

/**
 * Represents a player-controlled hero participating in the tower climb.
 *
 * Students: you may extend this class as needed for your implementation.
 * You will need to add a HeroState field and related methods.
 */
public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense) {
        this(name, hp, attackPower, defense, new NormalState());
    }

    public Hero(String name, int hp, int attackPower, int defense, HeroState initialState) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = initialState;
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public HeroState getState()    { return state; }
    public String getStateName()   { return state.getName(); }
    public boolean isAlive()       { return hp > 0; }

    public void startTurn() {
        if (!isAlive()) {
            return;
        }

        System.out.println(name + " begins the turn in state " + state.getName() + " (" + hp + "/" + maxHp + " HP).");
        state.onTurnStart(this);

        if (!isAlive()) {
            System.out.println(name + " falls before being able to act.");
        }
    }

    public void endTurn() {
        if (!isAlive()) {
            return;
        }

        state.onTurnEnd(this);
        System.out.println(name + " ends the turn at " + hp + "/" + maxHp + " HP.");
    }

    public boolean canAct() {
        return isAlive() && state.canAct();
    }

    public int attack(Monster monster) {
        int damage = Math.max(1, state.modifyOutgoingDamage(attackPower));
        monster.takeDamage(damage);
        System.out.println(name + " strikes " + monster.getName() + " for " + damage + " damage. "
                + monster.getName() + " now has " + monster.getHp() + " HP.");
        return damage;
    }

    public void changeState(HeroState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("State cannot be null.");
        }

        String oldName = state == null ? "None" : state.getName();
        this.state = newState;
        if (!oldName.equals(newState.getName())) {
            System.out.println(name + " changes state: " + oldName + " -> " + newState.getName() + ".");
        }
    }

    public int sufferAttack(String source, int rawDamage) {
        int reducedByDefense = Math.max(1, rawDamage - defense);
        int finalDamage = Math.max(1, state.modifyIncomingDamage(reducedByDefense));
        hp = Math.max(0, hp - finalDamage);
        System.out.println(name + " takes " + finalDamage + " damage from " + source + " and is now at "
                + hp + "/" + maxHp + " HP.");
        return finalDamage;
    }

    public int sufferPureDamage(String source, int amount) {
        int finalDamage = Math.max(1, amount);
        hp = Math.max(0, hp - finalDamage);
        System.out.println(name + " suffers " + finalDamage + " direct damage from " + source + " and is now at "
                + hp + "/" + maxHp + " HP.");
        return finalDamage;
    }

    public int restoreHealth(String source, int amount) {
        int previousHp = hp;
        hp = Math.min(maxHp, hp + Math.max(0, amount));
        int restored = hp - previousHp;
        System.out.println(name + " recovers " + restored + " HP from " + source + " and is now at "
                + hp + "/" + maxHp + " HP.");
        return restored;
    }

    /**
     * Reduces this hero's HP by the given amount, clamped to zero.
     *
     * @param amount the damage to apply; must be non-negative
     */
    public void takeDamage(int amount) {
        sufferAttack("an unspecified attack", amount);
    }

    /**
     * Restores this hero's HP by the given amount, clamped to maxHp.
     *
     * @param amount the HP to restore; must be non-negative
     */
    public void heal(int amount) {
        restoreHealth("a generic heal", amount);
    }
}
