package com.example.android.stonepaperscissors;

public class player {
    private int choice;
    private int winsRound;

    public player() {
        this.choice = 0;
        this.winsRound = 0;
    }
    public void setChoice(int c){
        choice=c;
    }
    public void incrementWin(){
        winsRound++;
    }

    public int getChoice() {
        return choice;
    }

    public int getWinsRound() {
        return winsRound;
    }
}
