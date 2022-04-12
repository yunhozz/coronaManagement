package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.VaccinationPerson;

import java.util.List;

public interface PersonRepositoryCustom {

    List<VaccinationPerson> findVpWithVaccine();
    List<InfectedPerson> findIpWithVirusHospital(int offset, int limit);
    List<InfectedPerson> findIpWithContactedRoute(int offset, int limit);
    List<InfectedPerson> findAllForIp(int offset, int limit);
}
