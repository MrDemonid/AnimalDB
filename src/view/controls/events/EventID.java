package view.controls.events;

public enum EventID {
    FILTER_DATE(200, "FilterDate"),
    FILTER_TYPE(201, "FilterType"),
    FILTER_ALL(202, "FilterAll"),
    ANIMAL_NEW(300, "NewAnimal"),
    ANIMAL_UPDATE(301, "UpdateAnimal");

    private final int code;
    private final String value;


    EventID(int code, String value)
    {
        this.code = code;
        this.value = value;
    }

    public int getCode()
    {
        return code;
    }

    public String getValue()
    {
        return value;
    }
}
