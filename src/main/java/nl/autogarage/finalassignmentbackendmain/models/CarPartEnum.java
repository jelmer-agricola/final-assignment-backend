package nl.autogarage.finalassignmentbackendmain.models;

import java.util.stream.DoubleStream;

public enum CarPartEnum {
    BRAKES("remmen"),
    BATTERIES("batterijen");
//    ,
//    TIRES("banden"),
//    LIGHTS("verlichting"),
//    WINDSHIELD_WIPERS("ruitenwissers"),
//    SUSPENSION("ophanging"),
//    ENGINE("motor");

    private final String translatedName;

    private CarPartEnum(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getTranslatedName() {
        return translatedName;
    }
}
