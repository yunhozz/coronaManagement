package coronaManagement.domain.person.service;

import coronaManagement.domain.person.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
}
