package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {

    private int turnsRemaining;

    public PoisonedState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Poisoned(" + turnsRemaining + ")";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, (basePower * 3) / 4);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(1, (rawDamage * 5) / 4);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is wracked by poison.");
        hero.sufferPureDamage("poison", 3);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        if (turnsRemaining <= 1 && hero.isAlive()) {
            hero.changeState(new NormalState());
            return;
        }
        turnsRemaining--;
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
