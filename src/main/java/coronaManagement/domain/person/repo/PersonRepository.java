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
    @Query("select nv from NotVaccinationPerson nv where nv.infectionStatus = 'BEFORE_INFECT' or nv.infectionStatus = 'INFECTED'")
    List<NotVaccinationPerson> findNotVaccinationPerson();

    //감염자 조회
    @Query("select ip from InfectedPerson ip")
    List<InfectedPerson> findInfectedPerson();

    //밀접 접촉자 조회
    @Query("select cp from ContactedPerson cp")
    List<ContactedPerson> findContactedPerson();

    //감염 전 상태인지 판단
    @Query("select case when p.infectionStatus = 'INFECTED' then true else false end from Person p")
    boolean findPersonWhoInfectedOrNot();

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

    //평균 나이를 넘는 사람들 조회
    @Query("select p from Person p where p.age > (select avg(p1.age) from Person p1)")
    List<T> findPeopleOverThanAvgAge();

    //백신 재접종 대상자 조회
    @Query("select p from Person p where p.vaccinationCount < :nextVaccinationCount and p.vaccinationCount > 0")
    List<T> findPeopleWhoMustReVaccination(@Param("nextVaccinationCount") int nextVaccinationCount);

    //백신 재접종 가능한 사람인지 조회
    @Query("select p from Person p where p.id = :personId and p.vaccinationCount > 0")
    Optional<T> findPersonWhoCanReVaccination(@Param("personId") Long personId);

    @Query("select case when p.physicalStatus = 'INFECTED' then true else false end from Person p")
    boolean findInfectedPersonWhoInfectedOrNot();

    @Query("select case when p.physicalStatus = 'HOSPITALIZED' then true else false end from Person p")
    boolean findInfectedPersonWhoHospitalized();

    @Query("select case when p.physicalStatus = 'DEAD' then true else false end from Person p")
    boolean findInfectedPersonWhoDead();

    //감염자 중 격리 조치된 사람 조회
    @Query("select p from Person p where p.physicalStatus = 'ISOLATED'")
    List<T> findPeopleWhoInfectedAndIsolated();

    //감염자 중 병원에 이송된 사람 조회
    @Query("select p from Person p where p.physicalStatus = 'HOSPITALIZED'")
    List<T> findPeopleWhoInfectedAndHospitalized();
}
