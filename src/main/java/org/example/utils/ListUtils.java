package org.example.utils;

import java.util.Collections;
import java.util.List;

public class ListUtils {
    /**
     * Compares two lists for equality, ignoring order but considering duplicates.
     *
     * @param list1 the first list to compare
     * @param list2 the second list to compare
     * @param <T>   the type of elements in the lists
     * @return true if the lists contain the same elements with the same frequency, false otherwise
     */
    public static <T> boolean areListsEqualIgnoringOrder(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            return list1 == list2; // true if both are null, false otherwise
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        return list1.stream().allMatch(item ->
                Collections.frequency(list1, item) == Collections.frequency(list2, item)
        );
    }

    // Static method to print items in a list
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }
}
