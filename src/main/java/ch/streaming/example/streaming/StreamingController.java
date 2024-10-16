package ch.streaming.example.streaming;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@RestController
public class StreamingController {

    private ExecutorService executor
            = Executors.newCachedThreadPool();

    @GetMapping("/stream")
    public ResponseEntity<StreamingResponseBody> handleRbe() {
        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= 1000; i++) {
                try {
                    Thread.sleep(10);
                    response.write(("Data stream line - " + i + "\n").getBytes());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }


//    @GetMapping(value = "/steam2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//    public Flux<Temerature> streamJsonObjects() {
//        return Flux.interval(Duration.ofSeconds(1)).map(i -> new Temerature("Name" + i, i.intValue()));
//    }
}
