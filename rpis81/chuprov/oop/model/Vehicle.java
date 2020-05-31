package rpis81.chuprov.oop.model;

import java.util.Objects;

public final class Vehicle implements Cloneable {

    private String registrationNumber;
    private String manufacturer;
    private String model;
    private VehicleTypes type;
    private final static String DEFAULT_DATA = "";
    public final static VehicleTypes DEFAULT_TYPE = VehicleTypes.NONE;
    private final static Vehicle NO_VEHICLE = new Vehicle(DEFAULT_TYPE);

    public Vehicle(String registrationNumber, String manufacturer, String model, VehicleTypes type) {
        this.registrationNumber = registrationNumber;
        this.manufacturer = manufacturer;
        this.model = model;
        this.type = type;
    }

    public Vehicle(VehicleTypes type) {
        this(DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA, type);
    }

    public Vehicle() {
        this(DEFAULT_TYPE);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleTypes getType() {
        return type;
    }

    public void setType(VehicleTypes type) {
        this.type = type;
    }

    public static Vehicle getNoVehicle() {
        return NO_VEHICLE;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s), registration number: %s",
                manufacturer, model, type.getValue(), registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, manufacturer, model, type);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) obj;
        return Objects.equals(registrationNumber, other.registrationNumber) &&
                Objects.equals(manufacturer, other.manufacturer) &&
                Objects.equals(model, other.model) &&
                Objects.equals(type, other.type);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}