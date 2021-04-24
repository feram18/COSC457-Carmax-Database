package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.dto.SiteManagerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "site_manager", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("SITE_MANAGER")
@EqualsAndHashCode(callSuper = true)
public class SiteManager extends Employee {
    @JsonBackReference
    @OneToOne(mappedBy = "siteManager", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Location location;
    @JsonManagedReference
    @OneToMany(mappedBy = "siteManager", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private List<Manager> managers;

    public SiteManager(String ssn,
                       String firstName,
                       Character middleInitial,
                       String lastName,
                       Gender gender,
                       LocalDate dateOfBirth,
                       String phoneNumber,
                       String email,
                       Location workLocation,
                       Double salary,
                       LocalDate dateStarted,
                       Address address,
                       Double hoursWorked,
                       EmployeeType employeeType,
                       Location location,
                       List<Manager> managers) {
        super(ssn,
                firstName,
                middleInitial,
                lastName,
                gender,
                dateOfBirth,
                phoneNumber,
                email,
                workLocation,
                salary,
                dateStarted,
                address,
                hoursWorked,
                employeeType);
        this.location = location;
        this.managers = managers;
    }

    public void assignManager(Manager manager) {
        managers.add(manager);
    }

    public void removeManager(Manager manager) {
        managers.remove(manager);
    }

    public static SiteManager from (SiteManagerDto siteManagerDto) {
        SiteManager siteManager = new SiteManager();
        siteManager.setLocation(siteManagerDto.getLocation());
        siteManager.setManagers(siteManagerDto.getManagers()
                .stream().map(Manager::from).collect(Collectors.toList()));
        return siteManager;
    }
}
