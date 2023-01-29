package com.mineme.server.entity;

import com.mineme.server.entity.enums.RegionCode;
import lombok.Getter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
public class Region{

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "REGION_CODE")
    private RegionCode regionCode;

    @Column(name = "REGION_NAME")
    @NotNull
    private String regionName;

    @Column(name = "SUB_REGION_NAME")
    private String subRegionName;
}
