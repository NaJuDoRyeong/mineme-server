package com.mineme.server.entity;

import com.mineme.server.entity.enums.RegionCode;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Region{

    /* ENUM으로 지정 */
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "REGION_CODE")
    private RegionCode regionCode;

    @Column(name = "REGION_NAME")
    private String regionName;

    @Column(name = "SUB_REGION_NAME")
    private String subRegionName;
}
