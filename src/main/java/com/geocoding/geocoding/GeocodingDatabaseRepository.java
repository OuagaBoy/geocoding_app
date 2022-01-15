package com.geocoding.geocoding;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeocodingDatabaseRepository extends JpaRepository<GeocodingDatabase, Long > {
    
}
