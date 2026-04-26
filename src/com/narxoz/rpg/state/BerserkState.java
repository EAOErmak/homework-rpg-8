package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {

    private int turnsRemaining;

    public BerserkState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Berserk(" + turnsRemaining + ")";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, (basePower * 3) / 2);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(1, (rawDamage * 13) / 10);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " roars in a berserk fury.");
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
