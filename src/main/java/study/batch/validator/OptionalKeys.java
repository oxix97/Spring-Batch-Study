package study.batch.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OptionalKeys {
    VERSION("version")
    ;
    private final String key;

    public static String[] getKeys() {
        return Arrays.stream(OptionalKeys.values())
                .map(OptionalKeys::getKey)
                .toArray(String[]::new);
    }
}
