package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.RegeneratingState;
import java.util.List;

public class RestSanctumFloor extends TowerFloor {

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("[Setup] A sealed sanctuary opens, filled with pale incense.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] The challenge here is endurance: who most needs the blessing?");
        Hero weakestHero = BattleSupport.lowestHpHero(party);
        if (weakestHero != null) {
            weakestHero.restoreHealth("the sanctuary blessing", 12);
            weakestHero.changeState(new RegeneratingState(2));
        }

        boolean cleared = BattleSupport.hasLivingHeroes(party);
        String summary = cleared
                ? "The party catches its breath in the sanctum."
                : "No one remains to receive the blessing.";
        return new FloorResult(cleared, 0, summary);
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        System.out.println("[Loot] Hook override: this floor grants no treasure, only recovery.");
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        // Intentionally unused because shouldAwardLoot() returns false.
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The incense fades, but the blessing remains on the hero.");
    }

    @Override
    protected String getFloorName() {
        return "The Quiet Sanctum";
    }
}
