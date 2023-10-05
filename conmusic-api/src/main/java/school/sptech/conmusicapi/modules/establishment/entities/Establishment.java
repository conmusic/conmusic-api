package school.sptech.conmusicapi.modules.establishment.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.manager.entities.Manager;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "estabelecimento")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 14, unique = true)
    private String cnpj;

    @Column(name = "nome_fantasia", length = 45)
    private String fantasyName;

    @Column(name = "razao_social", length = 45)
    private String establishmentName;

    @Column(name = "telefone", length = 11)
    private String phoneNumber;

    @Column(name = "qtd_tomada_110")
    private Integer amount110Outlets;

    @Column(name = "qtd_tomada_220")
    private Integer amount220Outlets;

    @Column(name = "capacidade")
    private Integer capacity;

    @Column(name = "endereco", length = 45)
    private String address;

    @Column(name = "cidade", length = 45)
    private String city;

    @Column(name = "uf", length = 2)
    private String state;

    @Column(name = "cep", length = 8)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "fk_gerente")
    private Manager manager;

    @OneToMany(mappedBy = "establishment", fetch = FetchType.LAZY)
    private List<Event> events;

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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Event> getEvents() {
        return Objects.isNull(events) ? Collections.emptyList() : events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}