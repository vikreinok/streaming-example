package ch.streaming.example.streaming;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
@RestController
public class StreamingController {

    @GetMapping(value = "/api/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamTask() {
        SseEmitter emitter = new SseEmitter();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    Thread.sleep(100);

                    Result result = new Result(i, Status.values()[Math.min(i / 30, Status.values().length - 1)], mocData(i));

                    emitter.send(result);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }

        }).start();

        return emitter;
    }

    private static List<String> mocData(int extIndex) {
        List<String> dataSegment = new ArrayList<>();

        for (int index = 0; index < new Random().nextInt(0, 10); index++) {
            dataSegment.add(extIndex + "." + index);
        }
        return dataSegment;
    }
}
