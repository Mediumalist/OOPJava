package rpis81.chuprov.oop.model;

public final class Vehicle {

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
        StringBuilder builder = new StringBuilder("Vehicle info: ");
        builder.append("type - ").append(type.getValue())
                .append(", registration number - ").append(registrationNumber)
                .append(", manufacturer - ").append(manufacturer)
                .append(", model - ").append(model).append("\n");
        return builder.toString();
    }
}