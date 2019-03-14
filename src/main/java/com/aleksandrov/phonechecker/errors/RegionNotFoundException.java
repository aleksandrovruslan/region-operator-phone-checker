package com.aleksandrov.phonechecker.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegionNotFoundException extends RuntimeException {

    public RegionNotFoundException(String regionId) {
        super("could not find region " + regionId + ".");
    }

}
