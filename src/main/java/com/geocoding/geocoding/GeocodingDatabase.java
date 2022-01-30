package com.geocoding.geocoding;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class GeocodingDatabase {
    @Id
    @SequenceGenerator(
        name = "geocode_addresses_sequence",
        sequenceName = "geocode_addresses_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "geocode_addresses_sequence"
    )
    @Column(
        updatable = false
    )
    private Long uid;

    @Column(
        name = "geocoded_addresses",
        length = 5000
    )
    private String geocodedAddresses;

    // Constructor
    protected GeocodingDatabase(){}

    public GeocodingDatabase(String geocodedAddresses){
        this.geocodedAddresses = geocodedAddresses;
    }

    // Getters
    public Long getUid(){
        return uid;
    }

    public String getGeocodedAddresses(){
        return geocodedAddresses;
    }
    
}
