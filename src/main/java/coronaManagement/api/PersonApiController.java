package coronaManagement.api;

import coronaManagement.domain.person.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonApiController {

    private final PersonRepository personRepository;
}
