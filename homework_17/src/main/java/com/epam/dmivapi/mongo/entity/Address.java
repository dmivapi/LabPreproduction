package com.epam.dmivapi.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    String line1;
    String line2;
    String countryCode;
}
