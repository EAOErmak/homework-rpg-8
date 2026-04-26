package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.List;

final class BattleSupport {

    private BattleSupport() {
    }

    static boolean hasLivingHeroes(List<Hero> party) {
        return countLivingHeroes(party) > 0;
    }

    static int countLivingHeroes(List<Hero> party) {
        int count = 0;
        for (Hero hero : party) {
            if (hero.isAlive()) {
                count++;
            }
        }
        return count;
    }

    static Hero lowestHpHero(List<Hero> party) {
        Hero candidate = null;
        for (Hero hero : party) {
            if (!hero.isAlive()) {
                continue;
            }
            if (candidate == null || hero.getHp() < candidate.getHp()) {
                candidate = hero;
            }
        }
        return candidate;
    }

    static Hero highestHpHero(List<Hero> party) {
        Hero candidate = null;
        for (Hero hero : party) {
            if (!hero.isAlive()) {
                continue;
            }
            if (candidate == null || hero.getHp() > candidate.getHp()) {
                candidate = hero;
            }
        }
        return candidate;
    }

    static Hero highestAttackHero(List<Hero> party) {
        Hero candidate = null;
        for (Hero hero : party) {
            if (!hero.isAlive()) {
                continue;
            }
            if (candidate == null || hero.getAttackPower() > candidate.getAttackPower()) {
                candidate = hero;
            }
        }
        return candidate;
    }

    static Monster firstAliveMonster(List<Monster> monsters) {
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                return monster;
            }
        }
        return null;
    }

    static boolean hasLivingMonsters(List<Monster> monsters) {
        return firstAliveMonster(monsters) != null;
    }

    static String partyStatus(List<Hero> party) {
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
