package rpis81.chuprov.oop.model;

import rpis81.chuprov.oop.model.*;

public class Test {

    public static void main(String... args) {
        lab1tests();
    }

        private static void lab1tests() {
            testPersons();
            testVehicle();
            testSpaces();
            testParking();
        }

        private static void testPersons() {
            Person firstPerson = new Person("Alex", "Lebowski");
            Person secondPerson = new Person("Keanu", "Reeves");
            System.out.println("\nThere are some people:" + firstPerson.toString() + secondPerson.toString());
        }

        private static void testVehicle() {
            Vehicle vehicle = new Vehicle("K257TC", "BMW", "X5");
            System.out.println(vehicle.toString());
        }

        private static void testSpaces() {
            Space firstSpace = new Space();
            Space secondSpace = new Space(new Vehicle("K257TC", "BMW", "X5"),
                    new Person("Keanu", "Reeves"));
            System.out.println("\nIs first space empty? " + firstSpace.isEmpty());
            System.out.println("Is second space empty? " + secondSpace.isEmpty());
        }

        private static OwnersFloor testOwnersFloor() {
            OwnersFloor floor = new OwnersFloor(3);
            floor.add(new Space(new Vehicle("K257TC", "BMW", "X5"), new Person("Keanu", "Reeves")));
            floor.add(new Space(new Vehicle("T441AX", "Toyota", "RAV4"), new Person("Lee", "Kong")));
            floor.add(1, new Space(new Vehicle("A897AO", "Volkswagen", "Polo"),
                    new Person("Gregor", "McYorn")));
            System.out.println("\nFloor after space spreading: " + floor.toString());
            System.out.println("\nSpace found by number K257TC: " + floor.hasSpace("K257TC") + ", " +
                    floor.getSpace("K257TC").toString());
            System.out.println("\nSpace found by index 2: " + floor.getSpace(2).toString());
            floor.replaceWith(1, new Space(new Vehicle("X225HK", "UAZ", "Patriot"),
                    new Person("Nursultan", "Uzurbekov")));
            System.out.println("\nFloor after replacing: " + floor.toString());
            floor.remove("K257TC");
            System.out.println("\nFloor after removing: " + floor.toString());
            return floor;
        }

        private static void testParking() {
            OwnersFloor floor = testOwnersFloor();
            Parking parking = new Parking(2);
            parking.add(new OwnersFloor());
            floor.add(new Space(new Vehicle("P807OC", "Lada", "Vesta"),
                    new Person("Ivan", "Golovin")));
            parking.add(0, floor);
            System.out.print(parking.toString());
            System.out.print("\nSorted floors sizes: " + parking.getSortedFloors()[0].size() + ", " +
                    parking.getSortedFloors()[1].size());
            System.out.print("\nTotal count of vehicles: " + parking.getVechicles().length);
            parking.replaceFloor(0, floor);
            parking.replaceSpace(new Space(), "K257TC");
            parking.removeSpace("A897AO");
            parking.removeFloor(1);
            System.out.println("\nFinal state of parking: " + parking.toString());
        }
    }