package school.sptech.conmusicapi.modules.establishment.dtos;

public class DisplayScheduleEstablishmentDto {
    private Integer id;
    private String cnpj;
    private String fantasyName;
    private String establishmentName;
    private String phoneNumber;
    private Integer amount110Outlets;
    private Integer amount220Outlets;
    private Integer capacity;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private Integer managerId;
    private Double avaregeRating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAmount110Outlets() {
        return amount110Outlets;
    }

    public void setAmount110Outlets(Integer amount110Outlets) {
        this.amount110Outlets = amount110Outlets;
    }

    public Integer getAmount220Outlets() {
        return amount220Outlets;
    }

    public void setAmount220Outlets(Integer amount220Outlets) {
        this.amount220Outlets = amount220Outlets;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Double getAvaregeRating() {
        return avaregeRating;
    }

    public void setAvaregeRating(Double avaregeRating) {
        this.avaregeRating = avaregeRating;
    }
}
