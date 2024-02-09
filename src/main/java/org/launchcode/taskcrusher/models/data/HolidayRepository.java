package org.launchcode.taskcrusher.models.data;
import org.launchcode.taskcrusher.models.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Integer> {

    Optional<Holiday> findByHolidayDate(LocalDate holidayDate);
}