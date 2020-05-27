package rpis81.chuprov.oop.model;

public class Vehicle {

    private String registrationNumber;
    private String manufacturer;
    private String model;
    private static final String DEFAULT_DATA = "";

    public Vehicle(String registrationNumber, String manufacturer, String model) {
        this.registrationNumber = registrationNumber;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public Vehicle() {
        this(DEFAULT_DATA, DEFAULT_DATA, DEFAULT_DATA);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\nVehicle info: ");
        builder.append("registration number - ").append(registrationNumber)
                .append(", manufacturer - ").append(manufacturer)
                .append(", model - ").append(model);
        return builder.toString();
    }
}