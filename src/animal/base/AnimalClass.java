package animal.base;

public enum AnimalClass {
    Pets, Packs;

    static public AnimalClass getClass(String type)
    {
        if (type.equalsIgnoreCase(AnimalClass.Pets.name()))
            return AnimalClass.Pets;
        if (type.equalsIgnoreCase(AnimalClass.Packs.name()))
            return AnimalClass.Packs;
        return null;
    }
}
