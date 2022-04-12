package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.ContactedPerson;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.VaccinationPerson;
import coronaManagement.domain.routeInformation.RouteInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<VaccinationPerson> findVpWithVaccine() {
        return em.createQuery(
                "select vp from VaccinationPerson vp" +
                        " join fetch vp.vaccine v", VaccinationPerson.class)
                .getResultList();
    }

    @Override
    public List<InfectedPerson> findIpWithVirusHospital(int offset, int limit) {
        return em.createQuery(
                "select ip from InfectedPerson ip" +
                        " join fetch ip.virus v" +
                        " join fetch ip.hospital h", InfectedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<RouteInformation> findRouteWithInfectedPerson(int offset, int limit) {
        return em.createQuery(
                "select ri from RouteInformation ri" +
                        " join fetch ri.infectedPerson ip", RouteInformation.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<ContactedPerson> findCpWithInfectedRoute(int offset, int limit) {
        return em.createQuery(
                "select cp from ContactedPerson cp" +
                        " join fetch cp.routeInformation ri" +
                        " join fetch ri.infectedPerson ip", ContactedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<ContactedPerson> findAllWithContactedPerson(int offset, int limit) {
        return em.createQuery(
                "select cp from ContactedPerson cp" +
                        " join fetch cp.routeInformation ri" +
                        " join fetch ri.infectedPerson ip" +
                        " join fetch ip.virus v" +
                        " join fetch ip.hospital h", ContactedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
