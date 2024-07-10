package view.events;

import java.util.EventListener;

public interface NewAnimalListener extends EventListener {

    void actionPerformed(NewAnimalEvent event);
}
