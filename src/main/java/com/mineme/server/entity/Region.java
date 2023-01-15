package com.mineme.server.entity;

import com.mineme.server.entity.enums.RegionCode;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
public class Region extends BaseEntity{

    /* ENUM으로 지정 */
    @Id
    @Column(name = "REGION_CODE")
    private RegionCode regionCode;

    @Column(name = "REGION_NAME")
    private String regionName;

    @Column(name = "SUB_REGION_NAME")
    private String subRegionName;
}
