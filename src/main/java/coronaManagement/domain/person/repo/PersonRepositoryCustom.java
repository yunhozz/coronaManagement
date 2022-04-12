package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.ContactedPerson;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.VaccinationPerson;
import coronaManagement.domain.routeInformation.RouteInformation;

import java.util.List;

public interface PersonRepositoryCustom {

    List<VaccinationPerson> findVpWithVaccine();
    List<InfectedPerson> findIpWithVirusHospital(int offset, int limit);
    List<RouteInformation> findRouteWithInfectedPerson(int offset, int limit);
    List<ContactedPerson> findCpWithInfectedRoute(int offset, int limit);
    List<ContactedPerson> findAllWithContactedPerson(int offset, int limit);
}
