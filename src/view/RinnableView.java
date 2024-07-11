package view;

/**
 * Обертка для удобного запуска View в отдельном потоке
 */
public class RinnableView implements Runnable {

    View view;

    @Override
    public void run() {
        view = new SwingView();
    }

    public View getView() {
        return view;
    }
}
