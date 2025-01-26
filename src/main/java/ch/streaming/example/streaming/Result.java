package ch.streaming.example.streaming;

/**
 *
 */
public record Result(int percentage, Status status) {

}

enum Status {
    LOADING_DB1,
    LOADING_DB2,
    COMBINING,
}






