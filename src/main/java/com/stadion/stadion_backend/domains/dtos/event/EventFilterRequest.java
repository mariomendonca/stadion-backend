package com.stadion.stadion_backend.domains.dtos.event;

import com.stadion.stadion_backend.enums.EventCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class EventFilterRequest {
    private List<String> states;
    private List<EventCategory> categories;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer page;
    private Integer itemsPerPage;
}
