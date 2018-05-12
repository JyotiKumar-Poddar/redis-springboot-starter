package com.app.controller;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechEvent implements Serializable{

    private String eventId;
    private String eventName;
    private LocalDate eventDate;


}
