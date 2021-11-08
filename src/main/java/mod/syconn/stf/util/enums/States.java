package mod.syconn.stf.util.enums;

import net.minecraft.util.StringIdentifiable;

public enum States implements StringIdentifiable {

    ;

    private String name;
    private int id;

    States(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String asString() {
        return name;
    }
}
