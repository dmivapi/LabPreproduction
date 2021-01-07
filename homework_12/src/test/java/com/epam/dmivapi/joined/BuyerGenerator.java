package com.epam.dmivapi.joined;

import com.epam.dmivapi.joined.domain.Buyer;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class BuyerGenerator {
    private final String[] FIRST_NAMES = {"John", "Rita", "Rebecca", "Arnold"};
    private final String[] LAST_NAMES = {"Smith", "Jameson", "Ivanoff", "Gustavson"};

    public Buyer generate(int seed) {
        Random random = new Random(seed);
        Buyer buyer = new Buyer();
        buyer.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
        buyer.setLastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);
        return buyer;
    }
}
