package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class RegeneratingState implements HeroState {

    private int turnsRemaining;

    public RegeneratingState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Regenerating(" + turnsRemaining + ")";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, (basePower * 9) / 10);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return Math.max(1, (rawDamage * 3) / 4);
    }

    @Override
    public void onTurnStart(Hero hero) {
        hero.restoreHealth("regeneration", 4);
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
