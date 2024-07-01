package animal;

import java.util.ArrayList;
import java.util.Iterator;


public class AnimalCommands implements Iterable<String> {

    private ArrayList<String> commands;

    public AnimalCommands()
    {
        commands = new ArrayList<>();
    }

    public void addCommand(String cmd)
    {
        if (!commands.contains(cmd))
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

    /*
        Итератор для for-each
     */

    @Override
    public Iterator<String> iterator()
    {
        return new ImplCommands();
    }

    private class ImplCommands implements Iterator<String> {

        int index = 0;

        @Override
        public boolean hasNext()
        {
            return index < commands.size();
        }

        @Override
        public String next()
        {
            if (hasNext())
            {
                return commands.get(index++);
            }
            return null;
        }
    }
}