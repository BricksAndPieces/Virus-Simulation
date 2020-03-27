# Virus-Simulation

Video demonstration of simulation:
[![Virus Simulation Video](https://img.youtube.com/vi/KAYSk7BD_oI/0.jpg)](https://www.youtube.com/watch?v=KAYSk7BD_oI)

## How does it work?
A virus is spread by direct contact. The variables in the Constants class can be changed to change different aspects of the simulation.

```java
public class Constants {

    public static final int GRAPH_SIZE = 100; // Width of graph
    public static final int GRAPH_UPDATE_PER_FRAMES = 15; // Frames per graph update

    public static final int CITY_SIZE = 100; // Number of people
    public static final double MAX_SPEED = 0.5; // speed of people
    public static final double PEOPLE_SIZE = 2; // diameter of people
    public static final int INFECTION_TIME = 250; // how long people should have the virus

    public static final double HEAL_CHANCE = 0.50; // chance to heal
    public static final double INFECT_CHANCE = 0.75; // chance to infect

    public static final double HEALTH_INDUSTRY = 0.6; // what percent of the population the health industry can care for
}
```

## How to start?
Press any key to infect someone