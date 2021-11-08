package mod.syconn.stf.items.lightsaber.customize;

public enum LType {

    LUKE(1, "luke_skywalkers"),
    VADER(2, "Darth Vaders Lightsaber"),
    YODA(3, "master_yoda"),
    WHITE(4, "white_saber"),
    GUARD(5, "temple_guard"),
    WINDU(6, "mace_windu's");

    private int id;
    private String name;

    LType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static LType getType(int c){
        for (LType type : LType.values()){
            if (type.id == c)
                return type;
        }

        return LUKE;
    }
}
