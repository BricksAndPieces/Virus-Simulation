package simulation;

import entities.City;
import settings.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App extends JFrame {

    public static void main(String[] args) {
        App app = new App();
        app.init();
    }

    // ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- //

    private final Timer timer;
    private final CityPanel cityPanel;
    private final GraphPanel graphPanel;

    int counter = 0;
    boolean started = false;

    private  App() {
        super("Virus Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(!started) {
                    started = true;
                    cityPanel.getCity().getPeople()[0].setInfected();
                }
            }
        });

        this.cityPanel = new CityPanel(new City(Constants.CITY_SIZE));
        this.graphPanel = new GraphPanel(cityPanel.getCity());
        this.timer = new Timer(25, e -> {
            this.cityPanel.getCity().update();
            this.cityPanel.repaint();

            if(counter++ < 2 || (started && counter % Constants.GRAPH_UPDATE_PER_FRAMES == 0)) {
                this.graphPanel.update();
                this.graphPanel.repaint();
            }
        });

        this.cityPanel.setPreferredSize(new Dimension(500, 500));
        this.graphPanel.setPreferredSize(new Dimension(500, 500));

        cityPanel.setBackground(Color.decode("#e2c49c"));
        graphPanel.setBackground(Color.decode("#e2c49c"));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#d2b48c"));

        GridBagConstraints x = new GridBagConstraints();
        x.insets = new Insets(20, 20, 20, 20);

        mainPanel.add(cityPanel, x);

        x.gridx = 1;
        mainPanel.add(graphPanel, x);

        add(mainPanel);
    }

    private void init() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        timer.start();
    }
}