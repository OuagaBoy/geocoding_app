package com.geocoding.geocoding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeocodingDatabaseRepository extends JpaRepository<GeocodingDatabase, Long > {

    
}
