package com.zaberp.zab.biwtabackend.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface GenericController {
    String updateTable( @RequestBody Map<String, Object> payload);
}
