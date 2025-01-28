package ch.streaming.example.streaming;

import java.util.List;

/**
 *
 */
public record Result(int percentage, Status status, List<String> loadedData) {

}

enum Status {
    LOADING_DB1,
    LOADING_DB2,
    COMBINING,
}






