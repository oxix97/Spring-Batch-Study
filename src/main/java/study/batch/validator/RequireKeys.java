package study.batch.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RequireKeys {
    NAME("name"),
    DATE("date")
    ;
    private final String key;

    public static String[] getKeys() {
        return Arrays.stream(RequireKeys.values())
                .map(RequireKeys::getKey)
                .toArray(String[]::new);
    }
}
