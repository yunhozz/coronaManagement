package coronaManagement.domain.person;

import coronaManagement.domain.person.dto.PersonRequest;
import coronaManagement.domain.person.dto.PersonResponse;
import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.EachRecordRepository;
import coronaManagement.domain.routeInformation.RouteInformation;
import coronaManagement.domain.routeInformation.RouteInformationRepository;
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

        VaccinationPerson vaccinationPerson = (VaccinationPerson) personRequest.vaccinationPersonToEntity();
        vaccinationPerson.updateField(vaccine, eachRecord);

        vaccine.removeQuantity(1);
        eachRecord.addVaccination();

        personRepository.save(vaccinationPerson);

        return vaccinationPerson.getId();
    }

    public Long saveNotVaccinationPerson(PersonRequest personRequest) {
        NotVaccinationPerson notVaccinationPerson = (NotVaccinationPerson) personRequest.notVaccinationPersonToEntity();
        personRepository.save(notVaccinationPerson);

        return notVaccinationPerson.getId();
    }

    public Long saveInfectedPerson(PersonRequest personRequest, Long virusId, Long eachRecordId) {
        Virus virus = virusRepository.findById(virusId)
                .orElseThrow(() -> new IllegalStateException("Virus is empty."));

        EachRecord eachRecord = eachRecordRepository.findById(eachRecordId)
                .orElseThrow(() -> new IllegalStateException("EachRecord is empty."));

        InfectedPerson infectedPerson = (InfectedPerson) personRequest.infectedPersonToEntity();
        infectedPerson.updateField(virus, eachRecord);

        virus.addInfectionCount();
        eachRecord.addInfection();

        personRepository.save(infectedPerson);

        return infectedPerson.getId();
    }

    public Long saveContactedPerson(PersonRequest personRequest, RouteInformationRequest routeInformationRequest, Long infectedPersonId) {
        ContactedPerson contactedPerson = (ContactedPerson) personRequest.contactedPersonToEntity();
        RouteInformation routeInformation = routeInformationRequest.toEntity();

        InfectedPerson infectedPerson = (InfectedPerson) this.findPerson(infectedPersonId)
                .orElseThrow(() -> new IllegalStateException("InfectedPerson is empty."));

        routeInformation.updateField(infectedPerson);
        contactedPerson.updateField(routeInformation);

        personRepository.save(contactedPerson); //routeInformation auto persist

        return contactedPerson.getId();
    }

    public void reVaccination(Long personId, Long eachRecordId) {
        Optional<Person> optionalPerson = personRepository.findPersonWhoCanReVaccination(personId);
        EachRecord eachRecord = eachRecordRepository.findById(eachRecordId)
                .orElseThrow(() -> new IllegalStateException("EachRecord is empty."));

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException("Person is empty.");
        }

        VaccinationPerson vaccinationPerson = (VaccinationPerson) optionalPerson.get();

        vaccinationPerson.reVaccination();
        eachRecord.addVaccination();
    }

    public Long getInfected(Long personId, Long virusId, Long eachRecordId, PersonRequest personRequest) {
        Optional<Person> optionalPerson = personRepository.findById(personId);

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException("Person is empty.");
        }

        Person person = optionalPerson.get();

        Virus virus = virusRepository.findById(virusId)
                .orElseThrow(() -> new IllegalStateException("Virus is empty."));

        EachRecord eachRecord = eachRecordRepository.findById(eachRecordId)
                .orElseThrow(() -> new IllegalStateException("EachRecord is empty."));

        //감염자 -> true
        if (personRepository.findPersonWhoInfectedOrNot()) {
            throw new IllegalStateException("This person is already infected.");
        }

        virus.addInfectionCount();
        eachRecord.addInfection();

        String distinguishId = person.getId().toString();
        PersonResponse personResponse = new PersonResponse(person);

        personRepository.delete(person);

        createPersonRequestByResponse(personRequest, personResponse, distinguishId);
        InfectedPerson infectedPerson = (InfectedPerson) personRequest.infectedPersonToEntity();
        infectedPerson.updateField(virus, eachRecord);

        personRepository.save(infectedPerson);

        return infectedPerson.getId();
    }

    @Transactional(readOnly = true)
    public List<VaccinationPerson> findReVaccinationPeople(int nextVaccinationCount) {
        return personRepository.findPeopleWhoMustReVaccination(nextVaccinationCount);
    }

    @Transactional(readOnly = true)
    public Optional<Person> findPerson(Long personId) {
        return (Optional<Person>) personRepository.findById(personId);
    }

    @Transactional(readOnly = true)
    public List<Person> findPeople() {
        return personRepository.findAll();
    }

    private void createPersonRequestByResponse(PersonRequest personRequest, PersonResponse personResponse, String distinguishId) {
        personRequest.setName(personResponse.getName());
        personRequest.setCity(personResponse.getCity());
        personRequest.setGender(personResponse.getGender());
        personRequest.setAge(personResponse.getAge());
        personRequest.setPhoneNumber(personResponse.getPhoneNumber());
        personRequest.setDistinguishId(distinguishId);
    }
}
