package fr.uvsq.Max.RogueLikeMaven.Screen;

import asciiPanel.AsciiPanel;
import fr.uvsq.Max.RogueLikeMaven.Difficulty;
import fr.uvsq.Max.RogueLikeMaven.PlayerClass;


import java.awt.event.KeyEvent;

public class EarlyGameScreen implements  Screen{
    private Difficulty difficulty = Difficulty.NULL;
    private PlayerClass playerClass = PlayerClass.NULL;

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("ROGUE LIKE : PARAMETER" + "", 10, 1);
        terminal.write("DIFFICUTLY", 30, 4);
        terminal.write("Easy--[e]", 20, 5);
        terminal.write("Normal--[n]", 30, 5);
        terminal.write("Hard--[h]", 42, 5);
        terminal.write ("chosen Difficutly : "+ difficulty.nom(), 25, 7);
        if (this.difficulty.nom() != " "){
            terminal.write("AVAILABLE CLASS", 30, 9);
            terminal.write("WARRIOR--[w]", 20, 10);
            terminal.write("MAGE--[m]",40,10);
            terminal.write ("chosen player class : "+ playerClass.nom(), 25, 12);
        }
        if (this.difficulty.nom() == "EasyMode"){
            if (this.playerClass.nom() == "Warrior"){
                terminal.write("HP = 100 " + " MANA = 50", 30, 14);
                terminal.write("Weapon : Frostmourn", 30, 15);
                terminal.write("spell : *Healing Pulse", 30, 16);
            }
            else if (this.playerClass.nom()=="Mage"){
                terminal.write("HP = 80 " + " MANA = 100", 30, 14);
                terminal.write("Weapon : Aluneth", 30, 15);
                terminal.write("spell : *Healing Pulse", 30, 16);
                terminal.write("        *Arcan Blast", 30, 17);

            }

        }
        else if (this.difficulty.nom() == "NormalMode"){
            if (this.playerClass.nom() == "Warrior"){
                terminal.write("HP = 80 " + " MANA = 30", 30, 14);
                terminal.write("Weapon : Shyamalan", 30, 15);
                terminal.write("spell : *Healing wave", 30, 16);
            }
            else if (this.playerClass.nom()=="Mage"){
                terminal.write("HP = 60 " + " MANA = 80", 30, 14);
                terminal.write("Weapon : Ebonchill", 30, 15);
                terminal.write("spell : *Healing wave", 30, 16);
                terminal.write("        *Ebonbolt ", 30, 17);
            }

        }
        else if (this.difficulty.nom() == "HardMode"){
            if (this.playerClass.nom() == "Warrior"){
                terminal.write("HP = 40 " + " MANA = 10", 30, 14);
                terminal.write("Weapon : Gorehowl", 30, 15);
                terminal.write("spell : *small heal", 30, 16);
                terminal.write("        *Arcan Blast", 30, 17);
            }
            else if (this.playerClass.nom()=="Mage"){
                terminal.write("HP = 40 " + " MANA = 50", 30, 14);
                terminal.write("Weapon : Wooden staff", 30, 15);
                terminal.write("spell : *small heal", 30, 16);
                terminal.write("        *Firebolt", 30, 17);
            }

        }


        terminal.writeCenter("-- press [enter] to start --", 22);

    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ENTER: return new PlayScreen();
        }

        switch (key.getKeyChar()){
            case 'e': this.difficulty = Difficulty.EASY; break;
            case 'n': this.difficulty = Difficulty.NORMAL; break;
            case 'h': this.difficulty = Difficulty.HARD; break;
            case 'w': this.playerClass = PlayerClass.WARRIOR; break;
            case 'm' : this.playerClass = PlayerClass.MAGE; break;
        }
        return this;
    }

}