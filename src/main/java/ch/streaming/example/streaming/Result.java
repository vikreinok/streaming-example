package ch.streaming.example.streaming;

import java.util.List;

/**
 *
 */
public record Result(int percentage, Status status, List<String> data) {

}

enum Status {
    LOADING_DB1,
    LOADING_DB2,
    COMBINING,
}






