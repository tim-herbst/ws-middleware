package de.hft.timherbst.common;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class UuidEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false, updatable = false, columnDefinition = "uniqueidentifier")
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.PRIVATE)
    private UUID id;

    @Column(name = "INSERT_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTime = new Date();

    @Column(name = "UPDATE_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @PreUpdate
    @PrePersist
    public void setUpdateTimestamp() {
        updateTime = new Date();
    }
}
