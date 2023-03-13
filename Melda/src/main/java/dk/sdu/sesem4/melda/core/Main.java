package dk.sdu.sesem4.melda.core;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Melda";
        cfg.width = 600;
        cfg.height = 500;
        cfg.useGL30 = false;
        cfg.resizable = true;
        new LwjglApplication(new Game(),cfg);

    }
}