package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.NormalState;
import java.util.List;

public class NecromancerBossFloor extends TowerFloor {

    private Monster boss;

    @Override
    protected void announce() {
        System.out.println("\n--- Entering The Necromancer's Apex ---");
        System.out.println("A throne of bones stirs as the tower's master rises to meet the party.");
    }

    @Override
    protected void setup(List<Hero> party) {
        boss = new Monster("Necromancer Lord", 48, 16);
        System.out.println("[Setup] The boss seals the exits and raises a black staff.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("[Challenge] The final duel begins.");
        int damageTaken = 0;
        int round = 1;

        while (BattleSupport.hasLivingHeroes(party) && boss.isAlive()) {
            System.out.println("[Challenge] Boss round " + round + ".");

            for (Hero hero : party) {
                if (!hero.isAlive() || !boss.isAlive()) {
                    continue;
                }

                hero.startTurn();
                if (hero.isAlive() && hero.canAct()) {
                    hero.attack(boss);
                } else if (hero.isAlive()) {
                    System.out.println(hero.getName() + " cannot answer the boss this round.");
                }
                hero.endTurn();
            }

            if (!boss.isAlive() || !BattleSupport.hasLivingHeroes(party)) {
                break;
            }

            Hero target = BattleSupport.lowestHpHero(party);
            System.out.println("Necromancer Lord hurls a dark lance at " + target.getName() + ".");
            damageTaken += target.sufferAttack(boss.getName(), boss.getAttackPower());

            if (target.isAlive()
                    && target.getState() instanceof NormalState
                    && target.getHp() <= target.getMaxHp() / 3) {
                System.out.println(target.getName() + " is pushed into a desperate counter-rage.");
                target.changeState(new BerserkState(2));
            }

            round++;
        }

        boolean cleared = BattleSupport.hasLivingHeroes(party) && !boss.isAlive();
        String summary = cleared
                ? "The necromancer is defeated and the tower goes still."
                : "The necromancer remains undefeated.";
        return new FloorResult(cleared, damageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("[Loot] The shattered phylactery releases a final pulse of vitality.");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.restoreHealth("the broken phylactery", 6);
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("[Cleanup] The tower trembles as the apex curse dissolves.");
    }

    @Override
    protected String getFloorName() {
        return "The Necromancer's Apex";
    }
}
