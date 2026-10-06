package com.devfelipemilhomes.serviceOrder;

import com.devfelipemilhomes.client.Client;
import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.professional.Professional;
import com.devfelipemilhomes.serviceItem.ServiceItem;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.ServiceOrderPart;
import com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem.ServiceOrderServiceItem;
import com.devfelipemilhomes.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "service_order")
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrder")
    private Set<ServiceOrderPart> serviceOrderParts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrder")
    private Set<ServiceOrderServiceItem> serviceOrderServiceItems = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus status;

    @Column(name = "reported_problem")
    private String reportedProblem;

    private String diagnosis;

    private Integer odometer;

    @Column(name = "created_at", updatable = false)
    private final OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @Column(name = "cancelled_at")
    private OffsetDateTime cancelledAt;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public ServiceOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceOrderStatus status) {
        this.status = status;
    }

    public String getReportedProblem() {
        return reportedProblem;
    }

    public void setReportedProblem(String reportedProblem) {
        this.reportedProblem = reportedProblem;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(OffsetDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public OffsetDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(OffsetDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Set<ServiceOrderServiceItem> getServiceOrderServiceItems() {
        return serviceOrderServiceItems;
    }

    public void setServiceOrderServiceItems(Set<ServiceOrderServiceItem> serviceOrderServiceItems) {
        this.serviceOrderServiceItems = serviceOrderServiceItems;
    }

    public Set<ServiceOrderPart> getServiceOrderParts() {
        return serviceOrderParts;
    }

    public void setServiceOrderParts(Set<ServiceOrderPart> serviceOrderParts) {
        this.serviceOrderParts = serviceOrderParts;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceOrder serviceOrder = (ServiceOrder) o;

        return id != null && id.equals(serviceOrder.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
