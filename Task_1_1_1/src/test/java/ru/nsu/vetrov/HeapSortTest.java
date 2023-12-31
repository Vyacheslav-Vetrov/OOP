package ru.nsu.vetrov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Class tests the HeapSort class.
 */
public class HeapSortTest {

    /**
     * Tests array with odd number of elements.
     */
    @Test
    public void testBasicSortOdd() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    /**
     * Tests array with even number of elements.
     */
    @Test
    public void testBasicSortEven() {
        int[] input = {5, 4, 3, 2, 7, 1};
        int[] expected = {1, 2, 3, 4, 5, 7};
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    /**
     * Tests empty array.
     */
    @Test
    public void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }
}
