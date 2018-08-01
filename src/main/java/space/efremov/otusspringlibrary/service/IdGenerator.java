package space.efremov.otusspringlibrary.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class IdGenerator {

    private AtomicLong id = new AtomicLong(1000);

    public Long next() {
        return id.incrementAndGet();
    }
}
