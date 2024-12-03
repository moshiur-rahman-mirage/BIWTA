package com.zaberp.zab.biwtabackend.id;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MmprescriptionId implements Serializable {
    private Integer zid;
    private String xcase;
    private Integer xrow;
}

