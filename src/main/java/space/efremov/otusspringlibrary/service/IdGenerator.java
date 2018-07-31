package space.efremov.otusspringlibrary.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IdGenerator {

    private AtomicInteger id = new AtomicInteger(1000);

    public int next() {
        return id.incrementAndGet();
    }
}
