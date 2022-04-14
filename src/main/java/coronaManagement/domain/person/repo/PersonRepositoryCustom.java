package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.ContactedPerson;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.VaccinationPerson;

import java.util.List;

public interface PersonRepositoryCustom {

    List<VaccinationPerson> findVpWithVaccine();
    List<InfectedPerson> findIpWithVirus();
    List<InfectedPerson> findIpWithHospital();
    List<InfectedPerson> findIpWithVirusHospital();
    List<ContactedPerson> findCpWithRouteInformation();
    List<ContactedPerson> findCpWithInfectedRoute();
    List<ContactedPerson> findAllWithContactedPerson(int offset, int limit);
}
