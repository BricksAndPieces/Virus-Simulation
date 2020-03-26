package entities;

import entities.internal.Vector;
import settings.Constants;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("All")
public class City {

    private final int worldSize;
    private final Person[] people;

    public City(int worldSize) {
        this.worldSize = worldSize;
        this.people = new Person[worldSize];

        for(int i = 0; i < people.length; i++) {
            Vector position = new Vector(Math.random() * worldSize, Math.random() * worldSize);
            Vector velocity = new Vector(Math.random() * Constants.MAX_SPEED * 2, Math.random() * Constants.MAX_SPEED * 2);
            velocity.sub(Constants.MAX_SPEED);

            this.people[i] = new Person(position, velocity);
        }
    }

    public void update() {
        updatePeople();
        double percentInfected = Arrays.stream(people).filter(Person::isInfected).count() / ((double)people.length);
        Arrays.stream(people).forEach(p -> p.update(percentInfected));
    }

    private void updatePeople() {
        // Check world collison
        for(Person p : people) {
            if(p.getPosition().x < Constants.PEOPLE_SIZE && p.getVelocity().x < 0) {
                p.getVelocity().mult(new Vector(-1, 1));
                p.setChanged(true);
            }

            if(p.getPosition().x > worldSize - Constants.PEOPLE_SIZE && p.getVelocity().x > 0) {
                p.getVelocity().mult(new Vector(-1, 1));
                p.setChanged(true);
            }
            if(p.getPosition().y < Constants.PEOPLE_SIZE && p.getVelocity().y < 0) {
                p.getVelocity().mult(new Vector(1, -1));
                p.setChanged(true);
            }

            if(p.getPosition().y > worldSize - Constants.PEOPLE_SIZE && p.getVelocity().y > 0) {
                p.getVelocity().mult(new Vector(1, -1));
                p.setChanged(true);
            }
        }

        // Check person collision
        for(Person p : people) {
            if(p.isChanged() || p.isDead())
                continue;

            List<Person> closeBy = Arrays.asList(people).stream().filter(c -> !c.isChanged() && !c.isDead())
                                         .filter(c -> Vector.dist(p.getPosition(), c.getPosition()) < Constants.PEOPLE_SIZE)
                                         .collect(Collectors.toList());

            if(closeBy.size() > 1) {
                Vector temp = closeBy.get(0).getVelocity();
                for(int i = 0; i < closeBy.size()-1; i++) {
                    double separation = Vector.dist(closeBy.get(i).getPosition(), closeBy.get(i+1).getPosition());
                    double extra = (Constants.PEOPLE_SIZE - separation)/2;
                    Vector v = Vector.sub(closeBy.get(i).getPosition(), closeBy.get(i+1).getPosition());
                    v.normalize();
                    v.mult(extra);
                    closeBy.get(i).getPosition().add(v);

                    closeBy.get(i).setVelocity(closeBy.get(i + 1).getVelocity());
                }
                double separation = Vector.dist(closeBy.get(closeBy.size()-1).getPosition(), closeBy.get(0).getPosition());
                double extra = (Constants.PEOPLE_SIZE - separation)/2;
                Vector v = Vector.sub(closeBy.get(closeBy.size()-1).getPosition(), closeBy.get(0).getPosition());
                v.normalize();
                v.mult(extra);
                closeBy.get(closeBy.size()-1).getPosition().add(v);

                closeBy.get(closeBy.size()-1).setVelocity(temp);
                closeBy.stream().forEach(c -> c.setChanged(true));

                if(closeBy.stream().filter(x -> x.isInfected()).count() > 0) {
                    closeBy.forEach(x -> {
                        if(Math.random() < Constants.INFECT_CHANCE)
                            x.setInfected();
                    });
                }
            }
        }

        // Reset the changed state
        Arrays.stream(people).forEach(p -> p.setChanged(false));
    }

    public void drawPeople(Graphics2D g, int panelSize) {
        double scale = panelSize / ((double)worldSize);
        Arrays.stream(people).filter(p -> !p.isDead()).forEach(p -> {
            if(p.isInfected()) {
                g.setColor(Color.red);
            }else if(p.isHealed()) {
                g.setColor(Color.blue);
            }else {
                g.setColor(Color.black);
            }

            int x = (int)((p.getPosition().x - Constants.PEOPLE_SIZE/2)*scale);
            int y = (int)((p.getPosition().y - Constants.PEOPLE_SIZE/2)*scale);
            g.fillOval(x, y, (int)(Constants.PEOPLE_SIZE*scale), (int) (Constants.PEOPLE_SIZE*scale));
        });
    }

    public int getWorldSize() {
        return worldSize;
    }

    public Person[] getPeople() {
        return people;
    }
}