package coronaManagement.domain.hospital.repo;

import coronaManagement.domain.hospital.Hospital;

import java.util.List;

public interface HospitalRepositoryCustom {

    List<Hospital> findHospitalWithInfectedPerson(int offset, int limit);
}
