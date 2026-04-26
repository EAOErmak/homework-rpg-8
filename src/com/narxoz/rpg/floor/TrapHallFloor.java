package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.StunnedState;
import java.util.List;

public class TrapHallFloor extends TowerFloor {

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[Setup] Rusted pressure plates click beneath the party.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] Darts and falling stones sweep across the corridor.");
        int damageTaken = 0;

        for (Hero hero : party) {
            hero.startTurn();
        }

        for (Hero hero : party) {
            if (!hero.isAlive()) {
                continue;
            }
            damageTaken += hero.sufferPureDamage("the trap barrage", 6);
        }

        Hero stunnedTarget = BattleSupport.highestAttackHero(party);
        if (stunnedTarget != null && stunnedTarget.isAlive()) {
            stunnedTarget.changeState(new StunnedState(2));
        }

        for (Hero hero : party) {
            hero.endTurn();
        }

        boolean cleared = BattleSupport.hasLivingHeroes(party);
        String summary = cleared
                ? "The party pushes through the trapped hallway."
                : "The trapped hallway wipes out the party.";
        return new FloorResult(cleared, damageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[Loot] A hidden med-kit is recovered from a maintenance niche.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.restoreHealth("the med-kit", 2);
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The trap mechanisms grind back into place.");
    }

    @Override
    protected String getFloorName() {
        return "The Hall of Pressure Plates";
    }
}
