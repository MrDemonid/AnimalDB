package animal.base;

public enum AnimalSex {
    Male, Female, Unknown;

    static public AnimalSex getSex(String type)
    {
        if (type.equalsIgnoreCase(AnimalSex.Male.name()))
            return AnimalSex.Male;
        if (type.equalsIgnoreCase(AnimalSex.Female.name()))
            return AnimalSex.Female;
        return null;
    }

}
