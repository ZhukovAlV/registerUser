package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MessageId {

    private UUID uuid;
}
