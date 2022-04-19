package coronaManagement.domain.person;

import coronaManagement.domain.person.dto.PersonRequest;
import coronaManagement.domain.person.dto.PersonResponse;
import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.EachRecordRepository;
import coronaManagement.domain.routeInformation.dto.RouteInformationRequest;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.domain.virus.Virus;
import coronaManagement.domain.virus.VirusRepository;
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
    private final VaccineRepository vaccineRepository;
    private final VirusRepository virusRepository;
    private final EachRecordRepository eachRecordRepository;

    public Long saveVaccinationPerson(PersonRequest personRequest, Long vaccineId, Long eachRecordId) {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineId);
        Optional<EachRecord> optionalEachRecord = eachRecordRepository.findById(eachRecordId);

        if (optionalVaccine.isEmpty() || optionalEachRecord.isEmpty()) {
            throw new IllegalStateException("Vaccine or eachRecord is null.");
        }

        Vaccine vaccine = optionalVaccine.get();
        EachRecord eachRecord = optionalEachRecord.get();
        personRequest.setVaccine(vaccine);
        personRequest.setEachRecord(eachRecord);

        vaccine.removeQuantity(1);
        eachRecord.addVaccination();

        Person person = personRequest.vaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveNotVaccinationPerson(PersonRequest personRequest) {
        Person person = personRequest.notVaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveInfectedPerson(PersonRequest personRequest, Long virusId, Long eachRecordId) {
        Optional<Virus> optionalVirus = virusRepository.findById(virusId);
        Optional<EachRecord> optionalEachRecord = eachRecordRepository.findById(eachRecordId);

        if (optionalVirus.isEmpty() || optionalEachRecord.isEmpty()) {
            throw new IllegalStateException("Virus or eachRecord is null.");
        }

        Virus virus = optionalVirus.get();
        EachRecord eachRecord = optionalEachRecord.get();
        personRequest.setVirus(virus);
        personRequest.setEachRecord(eachRecord);

        virus.addInfectionCount();
        eachRecord.addInfection();

        Person person = personRequest.infectedPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveContactedPerson(PersonRequest personRequest, RouteInformationRequest routeInformationRequest, Long infectedPersonId) {
        Optional<Person> optionalPerson = personRepository.findById(infectedPersonId);

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException("Infected person is null.");
        }

        InfectedPerson infectedPerson = (InfectedPerson) optionalPerson.get();
        routeInformationRequest.setInfectedPerson(infectedPerson);

        Person person = personRequest.contactedPersonToEntity();
        personRepository.save(person); //routeInformation auto persist

        return person.getId();
    }

    public void reVaccination(Long personId) {
        Optional<VaccinationPerson> findPerson = personRepository.findPersonWhoCanReVaccination(personId);

        if (findPerson.isEmpty()) {
            throw new IllegalStateException("This person can't be re vaccinated.");
        }

        VaccinationPerson vaccinationPerson = findPerson.get();
        vaccinationPerson.reVaccination();
    }

    public void getInfected(Long personId, Long virusId, Long eachRecordId, PersonRequest personRequest) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Optional<Virus> optionalVirus = virusRepository.findById(virusId);
        Optional<EachRecord> optionalEachRecord = eachRecordRepository.findById(eachRecordId);

        if (optionalPerson.isEmpty() || optionalVirus.isEmpty() || optionalEachRecord.isEmpty()) {
            throw new IllegalStateException("Field is null.");
        }

        Person person = optionalPerson.get();
        Virus virus = optionalVirus.get();
        EachRecord eachRecord = optionalEachRecord.get();

        //감염자 -> true
        if (personRepository.findPersonWhoInfectedOrNot(person.getId())) {
            throw new IllegalStateException("This person is already infected.");
        }

        virus.addInfectionCount();
        eachRecord.addInfection();

        String dType = personRepository.findPersonWhereIncluded(person.getId()); //dType 조회
        String distinguishId = person.getId().toString();

        switch (dType) {
            case "V" -> {
                VaccinationPerson vaccinationPerson = (VaccinationPerson) person;
                PersonResponse personResponse = new PersonResponse(vaccinationPerson);

                createPersonRequestByResponse(personRequest, personResponse, virus, distinguishId);
                vaccinationPerson.getInfected();

                personRepository.save(personRequest.infectedPersonToEntity());
            }

            case "NV" -> {
                NotVaccinationPerson notVaccinationPerson = (NotVaccinationPerson) person;
                PersonResponse personResponse = new PersonResponse(notVaccinationPerson);

                createPersonRequestByResponse(personRequest, personResponse, virus, distinguishId);
                notVaccinationPerson.getInfected();

                personRepository.save(personRequest.infectedPersonToEntity());
            }

            case "C" -> {
                ContactedPerson contactedPerson = (ContactedPerson) person;
                PersonResponse personResponse = new PersonResponse(contactedPerson);

                createPersonRequestByResponse(personRequest, personResponse, virus, distinguishId);
                contactedPerson.getInfected();

                personRepository.save(personRequest.infectedPersonToEntity());
            }

            default -> throw new IllegalStateException("This person is already infected.");
        }
    }

    @Transactional(readOnly = true)
    public List<VaccinationPerson> findReVaccinationPeople(int nextVaccinationCount) {
        return personRepository.findPeopleWhoMustReVaccination(nextVaccinationCount);
    }

    @Transactional(readOnly = true)
    public Person findPerson(Long personId) throws Throwable {
        return (Person) personRepository.findById(personId)
                .orElseThrow(() -> new IllegalStateException("Can't find person."));
    }

    @Transactional(readOnly = true)
    public List<Person> findPeople() {
        return personRepository.findAll();
    }

    private void createPersonRequestByResponse(PersonRequest personRequest, PersonResponse personResponse, Virus virus, String distinguishId) {
        personRequest.setName(personResponse.getName());
        personRequest.setCity(personResponse.getCity());
        personRequest.setGender(personResponse.getGender());
        personRequest.setAge(personResponse.getAge());
        personRequest.setPhoneNumber(personResponse.getPhoneNumber());
        personRequest.setVirus(virus);
        personRequest.setDistinguishId(distinguishId);
    }
}
