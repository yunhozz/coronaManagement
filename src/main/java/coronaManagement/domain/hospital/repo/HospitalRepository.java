package coronaManagement.domain.hospital.repo;

import coronaManagement.domain.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalRepositoryCustom {

}