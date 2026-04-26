package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.ArrayList;
import java.util.List;

public class SkeletonCryptFloor extends TowerFloor {

    private final List<Monster> monsters = new ArrayList<Monster>();

    @Override
    protected void setup(List<Hero> party) {
        monsters.clear();
        monsters.add(new Monster("Bone Warden", 18, 8));
        monsters.add(new Monster("Crypt Bat", 12, 6));
        System.out.println("[Setup] Two undead guardians rise from the crypt dust.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] The floor becomes a small tactical battle.");
        int damageTaken = 0;
        int round = 1;

        while (BattleSupport.hasLivingHeroes(party) && BattleSupport.hasLivingMonsters(monsters)) {
            System.out.println("[Challenge] Combat round " + round + ".");

            for (Hero hero : party) {
                if (!hero.isAlive() || !BattleSupport.hasLivingMonsters(monsters)) {
                    continue;
                }

                hero.startTurn();
                if (hero.isAlive() && hero.canAct()) {
                    Monster target = BattleSupport.firstAliveMonster(monsters);
                    hero.attack(target);
                } else if (hero.isAlive()) {
                    System.out.println(hero.getName() + " loses the action for this round.");
                }
                hero.endTurn();
            }

            for (Monster monster : monsters) {
                if (!monster.isAlive() || !BattleSupport.hasLivingHeroes(party)) {
                    continue;
                }

                Hero target = BattleSupport.highestHpHero(party);
                System.out.println(monster.getName() + " lashes out at " + target.getName() + ".");
                damageTaken += target.sufferAttack(monster.getName(), monster.getAttackPower());
            }

            round++;
        }

        boolean cleared = BattleSupport.hasLivingHeroes(party) && !BattleSupport.hasLivingMonsters(monsters);
        String summary = cleared
                ? "The undead guardians are shattered."
                : "The crypt defenders hold the line.";
        return new FloorResult(cleared, damageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[Loot] A cracked reliquary releases restorative light.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.restoreHealth("the reliquary glow", 4);
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The bones settle and the crypt falls silent.");
    }

    @Override
    protected String getFloorName() {
        return "The Skeleton Crypt";
    }
}
