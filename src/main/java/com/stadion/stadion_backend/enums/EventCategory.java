package com.stadion.stadion_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventCategory {
    TENNIS("Tennis"),
    RUNNING("Running");

    private final String displayName;
}
