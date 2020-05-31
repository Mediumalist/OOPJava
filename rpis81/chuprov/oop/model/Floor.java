package rpis81.chuprov.oop.model;

public interface Floor {

    boolean addSpace(Space space);
    boolean addSpace(int index, Space space);
    Space getSpace(int index);
    Space getSpace(String registrationNumber);
    boolean hasSpace(String registrationNumber);
    Space replaceWith(int index, Space space);
    Space remove(int index);
    Space remove(String registrationNumber);
    int size();
    Space[] getSpaces();
    Vehicle[] getVehicles();
    boolean checkRegistrationNumber(Space space, String registrationNumber);
    boolean checkVehiclesType(Space space, VehicleTypes types);
    Space[] getSpacesByVehiclesType(VehicleTypes type);
    Space[] getFreeSpaces();
    int getSpacesCountByVehiclesType(VehicleTypes type);
}