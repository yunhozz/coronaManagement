package coronaManagement.domain.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepositoryCustom {

    private final EntityManager em;

    public List<VaccinationPerson> findPersonWithVaccine() {
        return em.createQuery(
                "select vp from VaccinationPerson vp" +
                        " join fetch vp.vaccine v", VaccinationPerson.class)
                .getResultList();
    }

    public List<InfectedPerson> findAllWithVirusHospital(int offset, int limit) {
        return em.createQuery(
                "select ip from InfectedPerson ip" +
                        " join fetch ip.virus v" +
                        " join fetch ip.hospital h", InfectedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<InfectedPerson> findAllWithContactedRoute(int offset, int limit) {
        return em.createQuery(
                "select distinct ip from InfectedPerson ip" +
                        " join fetch RouteInformation ri" +
                        " join fetch ContactedPerson cp", InfectedPerson.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
