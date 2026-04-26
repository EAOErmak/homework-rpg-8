package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunnedState implements HeroState {

    private int turnsRemaining;

    public StunnedState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Stunned(" + turnsRemaining + ")";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, basePower / 2);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(1, (rawDamage * 3) / 2);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is stunned and cannot act.");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        if (turnsRemaining <= 1 && hero.isAlive()) {
            if (hero.getHp() <= hero.getMaxHp() / 3) {
                hero.changeState(new BerserkState(2));
            } else {
                hero.changeState(new NormalState());
            }
            return;
        }
        turnsRemaining--;
    }

    @Override
    public boolean canAct() {
        return false;
    }
}
