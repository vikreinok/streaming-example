package ch.streaming.example.streaming;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 */
@RestController
public class StreamingController {



    @GetMapping(value = "/api/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamTask() {
        SseEmitter emitter = new SseEmitter();

        // Run the task asynchronously to avoid blocking the request thread
        new Thread(() -> {
            try {
                emitter.send(SseEmitter.event().name("status").data("Started").id("0"));

                for (int i = 1; i <= 100; i++) {
                    try {
                        Thread.sleep(10); // Simulate task progress
                    } catch (InterruptedException e) {
                        emitter.completeWithError(e);
                        return;
                    }
                    emitter.send(SseEmitter.event().name("status").data("Loading " + i).id(String.valueOf(i)));
                }

                emitter.send(SseEmitter.event().name("status").data("Done").id("100"));
                emitter.complete(); // Indicate task completion
            } catch (Exception e) {
                emitter.completeWithError(e); // Handle any errors
            }
        }).start();

        return emitter;
    }





//    @GetMapping(value = "/steam2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//    public Flux<Temerature> streamJsonObjects() {
//        return Flux.interval(Duration.ofSeconds(1)).map(i -> new Temerature("Name" + i, i.intValue()));
//    }
}
