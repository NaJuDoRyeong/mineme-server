package com.mineme.server.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegionCode {

    /* Seoul */
    SEOUL_GWANAK(02001, "SEOUL_GWANAK"),

    /* Incheon */

    /* Daejeon */

    /* Sejong */

    /* Daegu */

    /* Gwangju */

    /* Busan */

    /* Ulsan */

    /* Gyeonggi */

    /* Gangwon */

    /* Chungbuk */

    /* Chungnam */

    /* Jeonbuk */

    /* Jeonnam */

    /* Gyeonbuk */

    /* Gyeongnam */
    GYEONGNAM_CHANGWON(05301, "GYEONGNAM_CHANGWON");

    /* Jeju */

    private final int code;
    private final String region;
}
