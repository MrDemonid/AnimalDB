package view.events;

import java.util.EventListener;

public interface UpdateAnimalListener extends EventListener {

    void actionPerformed(UpdateAnimalEvent event);
}
