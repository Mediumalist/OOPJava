package rpis81.chuprov.oop.model;

public class Test {

    public static void main(String... args) {
        //lab1tests();
        //lab2tests();
        lab3tests();
    }

    /*
     *  Лабораторная №1
     */

    private static void lab1tests() {
        testPersons();
        testVehicles();
        testSpaces();
        testParking();
    }

    private static void testPersons() {
        Person firstPerson = new Person("Alex", "Lebowski");
        Person secondPerson = new Person("Keanu", "Reeves");
        Person thirdPerson = new Person("Vasya", "Pupkin");
        System.out.println("\nВот несколько человек:\n" + firstPerson.toString() + secondPerson.toString() +
                thirdPerson.toString());
    }

    private static void testVehicles() {
        Vehicle vehicle = new Vehicle("K257TC", "BMW", "X5", VehicleTypes.CAR);
        Vehicle secondVehicle = new Vehicle(VehicleTypes.MOTORBIKE);
        System.out.println("\nВот несколько машин: " + vehicle.toString() + secondVehicle.toString());
    }

    private static void testSpaces() {
        RentedSpace firstRentedSpace = new RentedSpace();
        RentedSpace secondRentedSpace = new RentedSpace(new Vehicle("K257TC", "BMW", "X5", VehicleTypes.CAR),
                new Person("Keanu", "Reeves"));
        RentedSpace thirdRentedSpace = new RentedSpace(new Vehicle(VehicleTypes.TRUCK), new Person("Vasya", "Pupkin"));
        System.out.println("\nПервое место пустое? " + firstRentedSpace.isEmpty());
        System.out.println("Второе место пустое? " + secondRentedSpace.isEmpty());
        System.out.println("Третье место пустое? " + thirdRentedSpace.isEmpty());
    }

    private static void testOwnersFloor() {
        OwnersFloor floor = createOwnersFloor();
        System.out.println("\nСостояние этажа после распределения мест: " + floor.toString());
        System.out.println("\nНайдено место по номеру K257TC: " + floor.hasSpace("K257TC") + ", " +
                floor.getSpace("K257TC").toString());
        System.out.println("\nНайдено место по индексу 2: " + floor.getSpace(2).toString());
        floor.replaceWith(1, new RentedSpace(new Vehicle("X225HK", "UAZ", "Patriot", VehicleTypes.CAR),
                new Person("Nursultan", "Uzurbekov")));
        System.out.println("\nСостояние этажа после замены места: " + floor.toString());
        floor.remove("K257TC");
        System.out.println("\nСостояние этажа после удаления места: " + floor.toString());
    }

    private static void testParking() {
        OwnersFloor floor = createOwnersFloor();
        Parking parking = new Parking(2);
        parking.add(new OwnersFloor());
        floor.addSpace(new RentedSpace(new Vehicle("P807OC", "Lada", "Vesta", VehicleTypes.CAR),
                new Person("Ivan", "Golovin")));
        parking.add(0, floor);
        System.out.print(parking.toString());
        System.out.print("\nРазмеры отсортированных этажей: " + parking.getSortedFloors()[0].size() + ", " +
                parking.getSortedFloors()[1].size());
        System.out.print("\nОбщее кол-во машин: " + parking.getVechicles().length);
        parking.replaceFloor(0, floor);
        parking.replaceSpace(new RentedSpace(), "K257TC");
        parking.removeSpace("A897AO");
        parking.removeFloor(1);
        System.out.println("\nКонечное состояние парковки");
        System.out.println(parking.toString());
    }

    /*
     *  Лабораторная №2
     */

    private static void lab2tests() {
        printFrame();
        Parking parking = new Parking(createOwnersFloor(), createRentedSpacesFloor());
        RentedSpacesFloor floor = (RentedSpacesFloor) parking.getFloor(1);
        printFrame();
        System.out.println("Тестирование нового добавленного класса - RentedSpaceFloor\n" +
                "Начальное состояние этажа");
        printFrame();
        floor.printSpaces();
        printFrame();
        System.out.println("Удаление парковочного места с индексом 2");
        printFrame();
        floor.remove(2);
        floor.printSpaces();
        printFrame();
        System.out.println("Добавление парковочного места по индексу 1");
        printFrame();
        floor.addSpace(1, new RentedSpace(new Vehicle("E005PP", "Geely", "NX", VehicleTypes.CAR),
                new Person("Paolo", "Manchini")));
        floor.printSpaces();
        printFrame();
        System.out.println("Замена парковочного места с индексом 2 ");
        printFrame();
        floor.replaceWith(2, new RentedSpace(new Vehicle("A897AO", "Volkswagen", "Polo", VehicleTypes.CAR),
                new Person("Gregor", "McYorn")));
        floor.printSpaces();
        printFrame();
        System.out.println("Добавление еще одного места в коне списка");
        printFrame();
        floor.addSpace(new RentedSpace(new Vehicle("K649TA", "Opel", "Astra", VehicleTypes.CAR),
                new Person("Vasya", "Pupkin")));
        floor.printSpaces();
        printFrame();
        System.out.println("Проверка наличия парковочных мест по заданному номеру");
        printFrame();
        System.out.println("Результат поиска для [X225HK]: " + floor.hasSpace("X225HK"));
        System.out.println("Результат поиска для [X100XX]: " + floor.hasSpace("X100XX") + "\n");
        printFrame();
        System.out.println("Список всех машин на этаже");
        printFrame();
        floor.printVehicles();
        printFrame();
    }

    private static RentedSpacesFloor createRentedSpacesFloor() {
        return new RentedSpacesFloor(new Space[] {
                new RentedSpace(new Vehicle("K257TC", "BMW", "X5", VehicleTypes.CAR),
                        new Person("Keanu", "Reeves")),
                new RentedSpace(new Vehicle("T441AX", "Toyota", "RAV4", VehicleTypes.CAR),
                        new Person("Lee", "Kong")),
                new RentedSpace(new Vehicle("A897AO", "Volkswagen", "Polo", VehicleTypes.CAR),
                        new Person("Gregor", "McYorn")),
                new RentedSpace(new Vehicle("X225HK", "UAZ", "Patriot", VehicleTypes.CROSSOVER),
                        new Person("Nursultan", "Uzurbekov"))
        });
    }

    private static OwnersFloor createOwnersFloor() {
        OwnersFloor floor = new OwnersFloor();
        for (Space space : createRentedSpacesFloor().getSpaces()) {
            floor.addSpace(new OwnedSpace(space.getVehicle(), space.getPerson()));
        }
        return floor;
    }

    private static void printFrame() {
        System.out.println("============================================" +
                "============================================");
    }

    /*
     *  Лабораторная №3
     */

    private static void lab3tests() {
        printFrame();
        System.out.println("Тестирование изменений в классе Vehicle");
        printFrame();
        Vehicle truck = new Vehicle("H7716OM", "Scania", "SL50", VehicleTypes.TRUCK);
        Vehicle bikeWithoutNumbers = new Vehicle(VehicleTypes.MOTORBIKE);
        Vehicle voidVehicle = new Vehicle();
        System.out.println("Типы созданных машин: " + truck.getType().getValue() + ", " +
                bikeWithoutNumbers.getType().getValue() + ", " + voidVehicle.getType().getValue());
        printFrame();
        System.out.println("Тестирование изменений в классах, реализующих интерфейс Space");
        printFrame();
        RentedSpace rentedSpace = new RentedSpace(new Person("Alexey", "Smolnikov"));
        OwnedSpace ownedSpace = new OwnedSpace(truck, new Person("Max", "Ludendorf"));
        System.out.println("Является ли rentedSpace пустым? " + rentedSpace.isEmpty());
        System.out.println("Является ли ownedSpace пустым? " + ownedSpace.isEmpty());
        System.out.println("Вызов метода setVehicle()...");
        rentedSpace.setVehicle(bikeWithoutNumbers);
        System.out.println("Является ли rentedSpace пустым? " + rentedSpace.isEmpty() + "\n");
        printFrame();
        System.out.println("Тестирование новых методов интерфейса Floor и класса Parking");
        printFrame();
        Parking parking = new Parking(createOwnersFloor(), createRentedSpacesFloor());
        OwnersFloor firstFloor = (OwnersFloor) parking.getFloor(0);
        RentedSpacesFloor secondFloor = (RentedSpacesFloor) parking.getFloor(1);
        System.out.println("Cписок кроссоверов на первом этаже");
        printFrame();
        firstFloor.printSpacesByVehiclesType(VehicleTypes.CROSSOVER);
        printFrame();
        System.out.println("Cписок автомобилей на втором этаже");
        printFrame();
        secondFloor.printSpacesByVehiclesType(VehicleTypes.CAR);
        printFrame();
        firstFloor.addSpace(new RentedSpace());
        firstFloor.addSpace(new RentedSpace());
        System.out.println("Кол-во пустых мест на первом этаже: " + firstFloor.getFreeSpaces().length);
        printFrame();
        secondFloor.addSpace(new RentedSpace());
        System.out.println("Oбщее число незанятых парковочных мест: " + parking.getFreeSpacesCount());
        printFrame();
        System.out.println("Общее число автомобилей: " + parking.getSpacesCountByVehiclesType(VehicleTypes.CAR));
        printFrame();
    }
}