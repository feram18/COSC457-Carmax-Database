package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.mapper.ServiceTicketMapper;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.model.dto.MechanicDto;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import edu.towson.cosc457.CarDealership.service.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mechanics")
public class MechanicController extends EmployeeController<MechanicService> {
    private final MechanicService mechanicService;
    private final EmployeeMapper employeeMapper;
    private final ServiceTicketMapper serviceTicketMapper;

    @Autowired
    public MechanicController(MechanicService mechanicService,
                              EmployeeMapper employeeMapper,
                              ServiceTicketMapper serviceTicketMapper) {
        super(mechanicService);
        this.mechanicService = mechanicService;
        this.employeeMapper = employeeMapper;
        this.serviceTicketMapper = serviceTicketMapper;
    }

    @PostMapping
    public ResponseEntity<MechanicDto> addEmployee(@RequestBody final MechanicDto mechanicDto) {
        Mechanic mechanic = mechanicService.addEmployee((Mechanic) employeeMapper.fromDto(mechanicDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<Mechanic> mechanics = mechanicService.getEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mechanics.stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MechanicDto> getEmployee(@PathVariable final Long id) {
        Mechanic mechanic = mechanicService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<MechanicDto> deleteEmployee(@PathVariable final Long id) {
        Mechanic mechanic = mechanicService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<MechanicDto> editEmployee(@PathVariable final Long id,
                                                    @PathVariable final MechanicDto mechanicDto) {
        Mechanic mechanic = mechanicService.editEmployee(id, (Mechanic) employeeMapper.fromDto(mechanicDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @GetMapping(value = "{id}/tickets")
    public ResponseEntity<List<ServiceTicketDto>> getAssignedTickets(@PathVariable final Long id) {
        Mechanic mechanic = mechanicService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mechanic.getTickets().stream().map(serviceTicketMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{mechanicId}/tickets/{ticketId}/add")
    public ResponseEntity<MechanicDto> assignTicket(@PathVariable final Long mechanicId,
                                                    @PathVariable final Long ticketId) {
        Mechanic mechanic = mechanicService.assignTicket(mechanicId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @DeleteMapping(value = "{mechanicId}/tickets/{ticketId}/remove")
    public ResponseEntity<MechanicDto> removeTicket(@PathVariable final Long mechanicId,
                                                    @PathVariable final Long ticketId) {
        Mechanic mechanic = mechanicService.removeTicket(mechanicId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }
}
