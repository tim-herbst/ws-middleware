package de.hft.timherbst.monument.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MonumentTestFactory {

    public static final MonumentTestFactory FACTORY = new MonumentTestFactory();
    private static final int MIN_CHARACTERS = 1;
    private static final int MAX_CHARACTERS = 500;

    private static String randomString() {
        return RandomStringUtils.random(RandomUtils.nextInt(MIN_CHARACTERS, MAX_CHARACTERS));
    }

    public Monument asEntity() {
        return asBuilder().build();
    }

    public Monument.MonumentBuilder asBuilder() {
        return Monument.builder()
                .address(randomString())
                .community(randomString())
                .county(randomString())
                .description(randomString())
                .photoUrl(randomString())
                .type(randomEnum(MonumentType.class))
                .objectNumber(RandomUtils.nextLong())
                .name(randomString());
    }

    public <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        final int r = RandomUtils.nextInt(0, clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[r];
    }
}