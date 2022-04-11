package coronaManagement.domain.person;

import coronaManagement.domain.hospital.Hospital;
import coronaManagement.domain.hospital.HospitalRepository;
import coronaManagement.global.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final HospitalRepository hospitalRepository;

//    public Long saveVaccinationPerson(PersonDto personDto) {
//
//    }
//
//    public Long saveNotVaccinationPerson(PersonDto personDto) {
//
//    }
//
//    public Long saveInfectedPerson(PersonDto personDto) {
//
//    }
//
//    public Long saveContactedPerson(PersonDto personDto) {
//
//    }
//
//    //백신 재접종 대상자 검색
//    @Transactional(readOnly = true)
//    public List<Person> findReVaccinationPerson(int nextVaccinationCount) {
//
//    }
//
//    //백신 재접종
//    public void reVaccination(Long personId) {
//
//    }
//
//    //감염 상태에서 회복
//    public void recover(Long personId) {
//
//    }
//
//    //감염 상태에서 죽음
//    public void passAway(Long personId) {
//
//    }
//
//    //백신 접종자 감염 처리
//    public void getInfectionForV(Long personId) {
//
//    }
//
//    //백신 미접종자 감염 처리
//    public void getInfectionForNV(Long personId) {
//
//    }
//
//    //밀접 접촉자 감염 처리
//    public void getInfectionForC(Long personId) {
//
//    }
//
//    @Transactional(readOnly = true)
//    public Person findPeople(Long personId) {
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<Person> findPerson() {
//
//    }
}
