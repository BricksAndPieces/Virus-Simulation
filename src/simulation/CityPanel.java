package simulation;

import entities.City;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CityPanel extends JPanel {

    private City city;
    private boolean x = false;

    public CityPanel(City city) {
        this.city = city;
    }

    @Override
    protected void paintComponent(Graphics _g) {
        super.paintComponent(_g);
        if(x) {
            Graphics2D g = (Graphics2D) _g;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            city.drawPeople(g, getPreferredSize().width);

            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);

            g.drawString(Arrays.stream(city.getPeople()).filter(p -> !p.isDead()).count()+"", 10, getHeight()-15);
        }else x = true;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}