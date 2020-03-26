package simulation;

import entities.City;
import entities.Person;
import settings.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphPanel extends JPanel {

    private City city;
    private List<Long> infected = new ArrayList<>();
    private List<Long> dead = new ArrayList<>();

    private boolean x = false;

    public GraphPanel(City city) {
        this.city = city;
    }

    public void update() {
        infected.add(Arrays.stream(city.getPeople()).filter(Person::isInfected).count());
        dead.add(Arrays.stream(city.getPeople()).filter(Person::isDead).count());
    }

    @Override
    public void repaint(Rectangle r) {
        if(infected.size() > 600)
            super.repaint(r);
    }

    @Override
    protected void paintComponent(Graphics g_) {
        super.paintComponent(g_);
        Graphics2D g = (Graphics2D) g_;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(x) {
            if(infected.size() < 2)
                return;

            g.setColor(Color.red);
            int xUnit = 600 / Constants.GRAPH_SIZE;
            int yUnit = getPreferredSize().height / city.getPeople().length;

            g.setColor(Color.red);
            for(int i = 0; i < infected.size()-1; i++) {
                g.drawLine(i*xUnit, (int)(getPreferredSize().height-(infected.get(i)*yUnit)), (i+1)*xUnit, getPreferredSize().height-(int)(infected.get(i+1)*yUnit));
            }

            g.setColor(Color.black);
            for(int i = 0; i < dead.size()-1; i++) {
                g.drawLine(i*xUnit, (int)(getPreferredSize().height-(dead.get(i)*yUnit)), (i+1)*xUnit, getPreferredSize().height-(int)(dead.get(i+1)*yUnit));
            }

            g.setColor(Color.blue);
            g.drawLine(0, (int)(getHeight()*(1-Constants.HEALTH_INDUSTRY)), getWidth()-1, (int)(getHeight()*(1-Constants.HEALTH_INDUSTRY)));

            g.setColor(Color.red);
            g.drawString("Infected", 5, 15);

            g.setColor(Color.black);
            g.drawString("Deaths", 5, 30);

//            g.setColor(Color.blue);
//            g.drawString("Health Industry", 5, 45);

            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        }else x = true;
    }

    public City getCity() {
        return city;
    }
}
