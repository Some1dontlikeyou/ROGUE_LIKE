package fr.uvsq.Max.RogueLikeMaven.Creatures;

import fr.uvsq.Max.RogueLikeMaven.World.Tile;
import fr.uvsq.Max.RogueLikeMaven.World.World;
import fr.uvsq.Max.RogueLikeMaven.World.Point;
import fr.uvsq.Max.RogueLikeMaven.World.WorldBuilder;
import fr.uvsq.Max.RogueLikeMaven.Inventory;
import fr.uvsq.Max.RogueLikeMaven.Item;

import java.awt.*;


public class Creature {
    private World world;

    public int x;
    public int y;
    public int z;

    private char glyph;
    public char glyph() { return glyph; }
    
    private Inventory inventory;
    public Inventory inventory() { return inventory; }

    private Color color;
    public Color color() { return color; }

    private CreatureAi ai;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

    private int maxHp;
    public int maxHp() { return maxHp; }

    private int hp;
    public int hp() { return hp; }
    public void setHP(int p_hp){ this.hp = p_hp;}

    private int maxMana;
    public int maxMana(){return maxMana;}

    private int mana;
    public int mana(){return mana;}
    public  void setMANA(int p_mana){ this.mana = p_mana;}

    private int attackValue;
    public int attackValue() { return attackValue; }

    private int defenseValue;
    public int defenseValue() { return defenseValue; }


    public Tile tile(int wx, int wy, int wz) {
        return world.tile(wx, wy, wz);
    }

    public Creature(World world, char glyph, Color color, int maxHp, int maxMana, int attack, int defense){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.maxMana= maxMana;
        this.hp = maxHp;
        this.mana = maxMana;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.inventory = new Inventory(20);
    }

    public void moveBy(int mx, int my, int mz){
        Tile tile = world.tile(x+mx, y+my, z+mz);

        if (mz == -1){
            if (tile == Tile.STAIRS_DOWN) {
                doAction("walk up the stairs to level %d", z+mz+1);
            } else {
                doAction("try to go up but are stopped by the cave ceiling");
                return;
            }
        } else if (mz == 1){
            if (tile == Tile.STAIRS_UP) {
                doAction("walk down the stairs to level %d", z+mz+1);
            } else {
                doAction("try to go down but are stopped by the cave floor");
                return;
            }
        }

        Creature other = world.creature(x+mx, y+my, z+mz);

        if (other == null)
            ai.onEnter(x+mx, y+my, z+mz, tile);
        else
            attack(other);
    }

    public void attack(Creature other){
        int amount = Math.max(0, attackValue() - other.defenseValue());

        amount = (int)(Math.random() * amount) + 1;

        doAction("attack the '%s' for %d damage", other.glyph, amount);

        other.modifyHp(-amount);
    }

    public void modifyHp(int amount) {
        hp += amount;

        if (hp < 1) {
            doAction("die");
            world.remove(this);
        }
    }

    public void dig(int wx, int wy, int wz) {
        world.dig(wx, wy, wz);
        doAction("dig");
    }

    public void update(){
        ai.onUpdate();
    }

    public boolean canEnter(int wx, int wy, int wz) {
        return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
    }

    public void notify(String message, Object ... params){
        ai.onNotify(String.format(message, params));
    }

    public void doAction(String message, Object ... params){
        int r = 9;
        for (int ox = -r; ox < r+1; ox++){
            for (int oy = -r; oy < r+1; oy++){
                if (ox*ox + oy*oy > r*r)
                    continue;

                Creature other = world.creature(x+ox, y+oy, z);

                if (other == null)
                    continue;

                if (other == this)
                    other.notify("You " + message + ".", params);
                else
                    other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
            }
        }
    }
    
    public void pickup(){
        Item item = world.item(x, y, z);
    
        if (inventory.isFull() || item == null){
            doAction("grab at the ground");
        } else {
            doAction("pickup a %s", item.name());
            world.remove(x, y, z);
            inventory.add(item);
        }
    }

    public void drop(Item item){
        doAction("drop a " + item.name());
        inventory.remove(item);
        world.addAtEmptySpace(item, x, y, z);
    }
    
    


    private String makeSecondPerson(String text){
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words){
            builder.append(" ");
            builder.append(word);
        }

        return builder.toString().trim();
    }
}