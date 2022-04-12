package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.VaccinationPerson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepositoryCustom {

    private final EntityManager em;

    public List<VaccinationPerson> findVpWithVaccine() {
        return em.createQuery(
                "select vp from VaccinationPerson vp" +
                        " join fetch vp.vaccine v", VaccinationPerson.class)
                .getResultList();
    }

    public List<InfectedPerson> findIpWithVirusHospital(int offset, int limit) {
        return em.createQuery(
                "select ip from InfectedPerson ip" +
                        " join fetch ip.virus v" +
                        " join fetch ip.hospital h", InfectedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<InfectedPerson> findIpWithContactedRoute(int offset, int limit) {
        return em.createQuery(
                "select distinct ip from InfectedPerson ip" +
                        " join fetch ip.routeInformation ri" +
                        " join fetch ri.contactedPerson cp", InfectedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<InfectedPerson> findAllForIp(int offset, int limit) {
        return em.createQuery(
                "select distinct ip from InfectedPerson ip" +
                        " join fetch ip.virus v" +
                        " join fetch ip.hospital h" +
                        " join fetch ip.routeInformation ri" +
                        " join fetch ri.contactedPerson cp", InfectedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
