package coronaManagement.domain.person.repo;

import coronaManagement.domain.person.*;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository<T extends Person> extends JpaRepository<T, Long>, PersonRepositoryCustom {

    //백신 접종자 조회
    @Query("select vp from VaccinationPerson vp")
    List<VaccinationPerson> findVaccinationPerson();

    //백신 미접종자 조회
    @Query("select nv from NotVaccinationPerson nv where nv.infectionStatus = BEFORE_INFECT or nv.infectionStatus = INFECTED")
    List<NotVaccinationPerson> findNotVaccinationPerson();

    //감염자 조회
    @Query("select ip from InfectedPerson ip")
    List<InfectedPerson> findInfectedPerson();

    //밀접 접촉자 조회
    @Query("select cp from ContactedPerson cp")
    List<ContactedPerson> findContactedPerson();

    //도시로 페이징
    @Query("select p from Person p")
    Page<T> findPageByCity(City city, Pageable pageable);

    //나이로 페이징
    @Query("select p from Person p")
    Page<T> findPageByAge(int age, Pageable pageable);

    //성별로 페이징
    @Query("select p from Person p")
    Page<T> findPageByGender(Gender gender, Pageable pageable);

    //성별과 나이로 조회
    @Query("select p from Person p where p.gender = :gender and p.age = :age")
    List<T> findPeopleWithGenderAndAge(@Param("gender") Gender gender, @Param("age") int age);

    //백신 재접종 대상자 조회
    @Query("select p from Person p where p.vaccinationCount < :nextVaccinationCount and p.vaccinationCount > 0")
    List<T> findPeopleWhoMustReVaccination(@Param("nextVaccinationCount") int nextVaccinationCount);

    //백신 재접종 가능한 사람인지 조회
    @Query("select p from Person p where p.id = :personId and p.vaccinationCount > 0")
    Optional<T> findPersonWhoCanReVaccination(@Param("personId") Long personId);

    //감염자 중 격리 조치된 사람 조회
    @Query("select p from Person p where p.physicalStatus = ISOLATED order by infectedTime")
    List<T> findPeopleWhoInfectedAndIsolated();

    //감염자 중 병원에 이송된 사람 조회
    @Query("select p from Person p where p.physicalStatus = HOSPITALIZED order by infectedTime")
    List<T> findPeopleWhoInfectedAndHospitalized();
}
