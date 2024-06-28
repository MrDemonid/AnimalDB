package animal;

import java.util.HashSet;


public class AnimalCommands {

    private HashSet<String> commands;

    public AnimalCommands()
    {
        commands = new HashSet<>();
    }

    public void addCommand(String cmd)
    {
        commands.add(cmd);
    }

    public boolean check(String cmd)
    {
        return commands.contains(cmd);
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        for (String command : commands)
        {
            if (!res.isEmpty())
                res.append(", ");
            res.append(command);
        }
        return res.toString();
    }
}
