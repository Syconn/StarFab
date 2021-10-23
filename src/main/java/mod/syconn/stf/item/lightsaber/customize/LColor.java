package mod.syconn.stf.item.lightsaber.customize;

import net.minecraft.util.DyeColor;

public enum LColor {

    BLUE(1, "blue"),
    RED(2, "red"),
    GREEN(3, "green"),
    WHITE(4, "white"),
    YELLOW(5, "yellow"),
    PURPLE(6, "purple");

    private int color;
    public String name;

    LColor(int c, String n) {
        color = c;
        name = n;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public DyeColor convert() {
        return switch (this) {
            default -> DyeColor.BLUE;
            case RED -> DyeColor.RED;
            case GREEN -> DyeColor.GREEN;
            case PURPLE -> DyeColor.PURPLE;
            case WHITE -> DyeColor.WHITE;
            case YELLOW -> DyeColor.YELLOW;
        };
    }

    public static LColor getColor(int c){
        for (LColor color : LColor.values()){
            if (color.color == c)
                return color;
        }

        return BLUE;
    }
}
