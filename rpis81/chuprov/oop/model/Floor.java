package rpis81.chuprov.oop.model;

public interface Floor {

    boolean add(Space space);
    boolean add(int index, Space space);
    Space get(int index);
    Space get(String registrationNumber);
    boolean hasSpace(String registrationNumber);
    boolean hasSpace(Person person);
    Space replaceWith(int index, Space space);
    Space remove(int index);
    Space remove(String registrationNumber);
    boolean remove(Space space);
    int indexOf(Space space);
    int getSpacesCountWithPerson(Person person);
    int size();
    Space[] getSpaces();
    Vehicle[] getVehicles();
    int getVehiclesCount();
    boolean checkRegistrationNumber(Space space, String registrationNumber);
    boolean checkVehiclesType(Space space, VehicleTypes types);
    Space[] getSpacesByVehiclesType(VehicleTypes type);
    Space[] getFreeSpaces();
    int getSpacesCountByVehiclesType(VehicleTypes type);
    String toString();
    int hashCode();
    boolean equals(Object obj);
    public Object clone() throws CloneNotSupportedException;
}