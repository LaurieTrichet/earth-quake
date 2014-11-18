package com.laurietrichet.earthquake.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

/**
 * Utility class to manipulate EarthQuake objects
 */
public class EarthQuakeUtility {

    public static enum SORTING_ORDER {DATE, MAGNITUDE}

    private final static EnumMap<SORTING_ORDER, Comparator> ORDER_COMPARATOR_ENUM_MAP
            = new EnumMap<SORTING_ORDER, Comparator>(SORTING_ORDER.class);

    static {
        ORDER_COMPARATOR_ENUM_MAP.put(SORTING_ORDER.DATE, EarthQuake.DATE_COMPARATOR);
        ORDER_COMPARATOR_ENUM_MAP.put(SORTING_ORDER.MAGNITUDE, EarthQuake.MAGNITUDE_COMPARATOR);
    }

    public static List<EarthQuake> sort(SORTING_ORDER sortingOrder, List<EarthQuake> earthQuakeList) {
        Collections.sort(earthQuakeList,
                Collections.reverseOrder(ORDER_COMPARATOR_ENUM_MAP.get(sortingOrder)));
        return earthQuakeList;
    }
}
