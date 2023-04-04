module Common {
    requires java.desktop;
    requires ShadedLibGDX;

    exports dk.sdu.sesem4.common.SPI;
    exports dk.sdu.sesem4.common.data.entity;
    exports dk.sdu.sesem4.common.data.events;
    exports dk.sdu.sesem4.common.data.gamedata;
    exports dk.sdu.sesem4.common.data.math;
    exports dk.sdu.sesem4.common.data.process;
    exports dk.sdu.sesem4.common.data.weapon;
    exports dk.sdu.sesem4.common.util;
}