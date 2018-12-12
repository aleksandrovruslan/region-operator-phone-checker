package com.aleksandrov.phonechecker.services.Update;

import java.util.concurrent.Phaser;

public class PhaserController {
    private final Runnable runnable;
    private final Phaser phaser;

    public PhaserController (Runnable saveToBase, Thread ... threads) {
        runnable = saveToBase;
        phaser = new Phaser(threads.length);
    }

    public void perform() {

    }
}
