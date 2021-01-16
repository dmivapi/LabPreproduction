package com.epam.dmivapi.messaging;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddendsMessage implements Serializable {
    private final int first;
    private final int second;
}
