package school.sptech.conmusicapi.modules.establishment.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;

public class UpdateEstablishmentDto {
    @NotBlank
    @CNPJ
    private String cnpj;

    @NotBlank
    @Size(min = 3, max = 45)
    private String fantasyName;

    @NotBlank
    @Size(min = 3, max = 45)
    private String establishmentName;

    @NotBlank
    @Pattern(
            regexp = "^\\s*(\\d{2}|\\d{0})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$",
            message = "Phone Number must be only digits"
    )
    @Size(max = 11)
    @Schema(example = "11909090808")
    private String phoneNumber;

    @PositiveOrZero
    private Integer amount110Outlets;

    @PositiveOrZero
    private Integer amount220Outlets;

    @Positive
    private Integer capacity;

    @NotBlank
    @Size(min = 5, max = 45)
    private String address;

    @NotBlank
    @Size(min = 2, max = 45)
    private String city;

    @NotBlank
    @Size(min = 2, max = 2)
    private String state;

    @NotBlank
    @Size(min = 8, max = 8)
    private String zipCode;

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
}
