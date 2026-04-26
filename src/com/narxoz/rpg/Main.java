package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.NecromancerBossFloor;
import com.narxoz.rpg.floor.RestSanctumFloor;
import com.narxoz.rpg.floor.SkeletonCryptFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.TrapHallFloor;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Hero> party = new ArrayList<Hero>();
        party.add(new Hero("Aria", 36, 11, 3, new NormalState()));
        party.add(new Hero("Borin", 42, 9, 4, new PoisonedState(2)));

        List<TowerFloor> floors = new ArrayList<TowerFloor>();
        floors.add(new TrapHallFloor());
        floors.add(new SkeletonCryptFloor());
        floors.add(new RestSanctumFloor());
        floors.add(new NecromancerBossFloor());

        TowerRunner towerRunner = new TowerRunner(floors);
        TowerRunResult result = towerRunner.run(party);

        System.out.println("\n=== Final Tower Run Result ===");
        System.out.println("Floors cleared: " + result.getFloorsCleared());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving());
        System.out.println("Reached top: " + result.isReachedTop());
    }
}
