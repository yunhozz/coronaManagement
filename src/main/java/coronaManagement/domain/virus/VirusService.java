package coronaManagement.domain.virus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VirusService {

    private final VirusRepository virusRepository;
}
