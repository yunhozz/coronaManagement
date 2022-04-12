package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.VaccinationPerson;

import java.util.List;

public interface PersonRepositoryCustom {

    List<VaccinationPerson> findPersonWithVaccine();
    List<InfectedPerson> findAllWithVirusHospital(int offset, int limit);
    List<InfectedPerson> findAllWithContactedRoute(int offset, int limit);
}
