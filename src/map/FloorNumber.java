package map;

public enum FloorNumber {
    FLOOR_LTWO("L2", 1),
    FLOOR_LONE("L1",2),
    FLOOR_ONE("1",3),
    FLOOR_TWO("2",4),
    FLOOR_THREE("3",5);
    private String dbMapping;
    private int nodeMapping;

    FloorNumber(String dbMapping, int nodeMapping) {
        this.dbMapping = dbMapping;
        this.nodeMapping = nodeMapping;
    }

    public String getDbMapping() {
        return dbMapping;
    }

    public int getNodeMapping() {
        return nodeMapping;
    }

    public static FloorNumber fromDbMapping(String dbMapping) {
        for (FloorNumber floorNumber : FloorNumber.values()) {
            if (floorNumber.dbMapping.equals(dbMapping)) {
                return floorNumber;
            }
        }
        return null;
    }
}
