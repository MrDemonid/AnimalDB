package animal.base;

public enum AnimalClass {
    Pets, Packs;

    static public AnimalClass getClass(String type)
    {
        if (type.equalsIgnoreCase(AnimalClass.Pets.toString()))
            return AnimalClass.Pets;
        if (type.equalsIgnoreCase(AnimalClass.Packs.toString()))
            return AnimalClass.Packs;
        return null;
    }
}
