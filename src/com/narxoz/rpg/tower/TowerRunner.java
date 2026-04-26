package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import java.util.ArrayList;
import java.util.List;

public class TowerRunner {

    private final List<TowerFloor> floors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = new ArrayList<TowerFloor>(floors);
    }

    public TowerRunResult run(List<Hero> party) {
        int floorsCleared = 0;

        for (TowerFloor floor : floors) {
            if (countLivingHeroes(party) == 0) {
                break;
            }

            FloorResult result = floor.explore(party);
            System.out.println("[Result] " + result.getSummary());
            System.out.println("[Result] Damage taken on floor: " + result.getDamageTaken());
            System.out.println("[Party] " + partySummary(party));

            if (!result.isCleared()) {
                break;
            }

            floorsCleared++;
        }

        boolean reachedTop = floorsCleared == floors.size() && countLivingHeroes(party) > 0;
        return new TowerRunResult(floorsCleared, countLivingHeroes(party), reachedTop);
    }

    private int countLivingHeroes(List<Hero> party) {
        int count = 0;
        for (Hero hero : party) {
            if (hero.isAlive()) {
                count++;
            }
        }
        return count;
    }

    private String partySummary(List<Hero> party) {
        StringBuilder builder = new StringBuilder();
        for (Hero hero : party) {
            if (builder.length() > 0) {
                builder.append(" | ");
            }
            builder.append(hero.getName())
                    .append(": ")
                    .append(hero.getHp())
                    .append("/")
                    .append(hero.getMaxHp())
                    .append(" HP, ")
                    .append(hero.getStateName());
        }
        return builder.toString();
    }
}
