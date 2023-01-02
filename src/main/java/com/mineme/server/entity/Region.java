package com.mineme.server.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Region {

    /* ENUM으로 지정 */
    @Id
    @Column(name = "REGION_CODE")
    private String regionCode;

    @Column(name = "REGION_NAME")
    private String regionName;

    @Column(name = "SUB_REGION_NAME")
    private String subRegionName;
}
