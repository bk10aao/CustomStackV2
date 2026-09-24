package customstackV2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CustomStackTest {

    @Test
    void testConstructorWithNullCollection() {
        assertThrows(NullPointerException.class, () -> new CustomStack<>(null));
    }

    @Test
    void testConstructorWithEmptyCollection() {
        List<String> emptyList = Collections.emptyList();
        CustomStack<String> stack = new CustomStack<>(emptyList);

        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testConstructorWithMultipleElements() {
        List<String> sourceList = Arrays.asList("apple", "banana", "cherry");
        CustomStack<String> stack = new CustomStack<>(sourceList);

        assertEquals(3, stack.size());
        assertEquals("apple", stack.get(0));
        assertEquals("banana", stack.get(1));
        assertEquals("cherry", stack.get(2));
    }

    @Test
    void testConstructorWithNullElements() {
        List<String> sourceList = Arrays.asList("apple", null, "cherry");
        CustomStack<String> stack = new CustomStack<>(sourceList);

        assertEquals(3, stack.size());
        assertEquals("apple", stack.get(0));
        assertNull(stack.get(1));
        assertEquals("cherry", stack.get(2));
    }


    @Test
    void testAddSingleElement() {
        CustomStack<Integer> stack = new customstackV2.CustomStack<>();
        boolean added = stack.add(10);

        assertTrue(added);
        assertEquals(1, stack.size());
        assertEquals(10, stack.get(0));
    }

    @Test
    void testAddMultipleElementsPreservesOrder() {
        CustomStack<Integer> stack = new CustomStack<>();

        stack.add(10);
        stack.add(20);
        stack.add(30);

        assertEquals(3, stack.size());
        assertEquals(10, stack.get(0));
        assertEquals(20, stack.get(1));
        assertEquals(30, stack.get(2));
    }

    @Test
    void testAddNullElement() {
        CustomStack<Integer> stack = new CustomStack<>();

        boolean added = stack.add(null);

        assertTrue(added);
        assertEquals(1, stack.size());
        assertNull(stack.get(0));
    }

    @Test
    void testAddIndexStart() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertTrue(stack.add(10));
        assertTrue(stack.add(0, 5));

        assertEquals(2, stack.size());
        assertEquals(5, stack.get(0));
        assertEquals(10, stack.get(1));
    }

    @Test
    void testAddIndexMiddle() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertTrue(stack.add(10));
        assertTrue(stack.add(30));
        assertTrue(stack.add(1, 20));

        assertEquals(3, stack.size());
        assertEquals(10, stack.get(0));
        assertEquals(20, stack.get(1));
        assertEquals(30, stack.get(2));
    }

    @Test
    void testAddIndexEnd() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertTrue(stack.add(10));
        assertTrue(stack.add(1, 20));

        assertEquals(2, stack.size());
        assertEquals(10, stack.get(0));
        assertEquals(20, stack.get(1));
    }

    @Test
    void testAddNullIndex() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertTrue(stack.add(0, null));

        assertEquals(1, stack.size());
        assertNull(stack.get(0));
    }

    @Test
    void testAddIndexOutOfBounds() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertThrows(IndexOutOfBoundsException.class, () -> stack.add(-1, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> stack.add(1, 5));
    }

    @Test
    void testAddAllSuccess() {
        CustomStack<Integer> stack = new CustomStack<>();
        List<Integer> list = Arrays.asList(1, 2, 3);

        boolean modified = stack.addAll(list);

        assertTrue(modified);
        assertEquals(3, stack.size());
        assertEquals(1, stack.get(0));
        assertEquals(2, stack.get(1));
        assertEquals(3, stack.get(2));
    }

    @Test
    void testAddAllEmptyCollection() {
        CustomStack<Integer> stack = new CustomStack<>();
        boolean modified = stack.addAll(Collections.emptyList());

        assertFalse(modified);
        assertEquals(0, stack.size());
    }

    @Test
    void testAddAllWithNullElements() {
        CustomStack<Integer> stack = new CustomStack<>();
        List<Integer> list = Arrays.asList(10, null, 30);

        boolean modified = stack.addAll(list);

        assertTrue(modified);
        assertEquals(3, stack.size());
        assertEquals(10, stack.get(0));
        assertNull(stack.get(1));
        assertEquals(30, stack.get(2));
    }

    @Test
    void testAddAllToNonEmptyStack() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(100);

        List<Integer> list = Arrays.asList(1, 2);
        boolean modified = stack.addAll(list);

        assertTrue(modified);
        assertEquals(3, stack.size());
        assertEquals(100, stack.get(0));
        assertEquals(1, stack.get(1));
        assertEquals(2, stack.get(2));
    }

    @Test
    void testAddAllNullCollectionThrowsException() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertThrows(NullPointerException.class, () -> stack.addAll(null));
    }

    @Test
    void testAddAllAtIndexStart() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(30);
        stack.add(40);

        List<Integer> list = Arrays.asList(10, 20);
        boolean modified = stack.addAll(0, list);

        assertTrue(modified);
        assertEquals(4, stack.size());
        assertEquals(10, stack.get(0));
        assertEquals(20, stack.get(1));
        assertEquals(30, stack.get(2));
        assertEquals(40, stack.get(3));
    }

    @Test
    void testAddAllAtIndexMiddle() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(40);

        List<Integer> list = Arrays.asList(20, 30);
        boolean modified = stack.addAll(1, list);

        assertTrue(modified);
        assertEquals(4, stack.size());
        assertEquals(10, stack.get(0));
        assertEquals(20, stack.get(1));
        assertEquals(30, stack.get(2));
        assertEquals(40, stack.get(3));
    }

    @Test
    void testAddAllAtIndexEnd() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);

        List<Integer> list = Arrays.asList(30, 40);
        boolean modified = stack.addAll(2, list);

        assertTrue(modified);
        assertEquals(4, stack.size());
        assertEquals(10, stack.get(0));
        assertEquals(20, stack.get(1));
        assertEquals(30, stack.get(2));
        assertEquals(40, stack.get(3));
    }

    @Test
    void testAddAllEmptyCollectionAtIndex() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);

        boolean modified = stack.addAll(0, Collections.emptyList());

        assertFalse(modified);
        assertEquals(1, stack.size());
        assertEquals(10, stack.get(0));
    }

    @Test
    void testAddAllWithNullElementsAtIndex() {
        CustomStack<Integer> stack = new CustomStack<>();
        List<Integer> list = Arrays.asList(1, null, 3);

        boolean modified = stack.addAll(0, list);

        assertTrue(modified);
        assertEquals(3, stack.size());
        assertEquals(1, stack.get(0));
        assertNull(stack.get(1));
        assertEquals(3, stack.get(2));
    }

    @Test
    void testAddAllIndexOutOfBounds() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);

        List<Integer> list = Arrays.asList(20, 30);

        assertThrows(IndexOutOfBoundsException.class, () -> stack.addAll(-1, list));
        assertThrows(IndexOutOfBoundsException.class, () -> stack.addAll(2, list));
    }

    @Test
    void testAddAllNullCollectionAtIndexThrowsException() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertThrows(NullPointerException.class, () -> stack.addAll(0, null));
    }



    @Test
    void testClearEmptyStack() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.clear();

        assertEquals(0, stack.size());
    }

    @Test
    void testClearNonEmptyStack() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.add(30);

        assertEquals(3, stack.size());

        stack.clear();

        assertEquals(0, stack.size());
        assertThrows(IndexOutOfBoundsException.class, () -> stack.get(0));
    }

    @Test
    void testReuseAfterClear() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.clear();

        assertEquals(0, stack.size());

        stack.add(99);
        assertEquals(1, stack.size());
        assertEquals(99, stack.get(0));
    }

    @Test
    void testContainsExistingElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertTrue(stack.contains("apple"));
        assertTrue(stack.contains("banana"));
    }

    @Test
    void testContainsNonExistingElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertFalse(stack.contains("banana"));
    }

    @Test
    void testContainsOnEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        assertFalse(stack.contains("apple"));
    }

    @Test
    void testContainsNullWhenPresent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);

        assertTrue(stack.contains(null));
    }

    @Test
    void testContainsNullWhenNotPresent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertFalse(stack.contains(null));
    }

    @Test
    void testContainsAllSuccess() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.add(30);

        List<Integer> list = Arrays.asList(10, 30);
        assertTrue(stack.containsAll(list));
    }

    @Test
    void testContainsAllMissingElement() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);

        List<Integer> list = Arrays.asList(10, 40);
        assertFalse(stack.containsAll(list));
    }

    @Test
    void testContainsAllEmptyCollection() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        assertTrue(stack.containsAll(Collections.emptyList()));
    }

    @Test
    void testContainsAllWithNulls() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");

        List<String> list = Arrays.asList(null, "apple");
        assertTrue(stack.containsAll(list));
    }

    @Test
    void testContainsAllOnEmptyStack() {
        CustomStack<Integer> stack = new CustomStack<>();

        assertFalse(stack.containsAll(List.of(10)));
        assertTrue(stack.containsAll(Collections.emptyList()));
    }

    @Test
    void testContainsAllNullCollectionThrowsException() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertThrows(NullPointerException.class, () -> stack.containsAll(null));
    }

    @Test
    void testIsEmptyOnNewStack() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testEmptyAfterAdd() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        assertFalse(stack.isEmpty());
    }

    @Test
    void testEmptyAfterMultipleAdds() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.add(30);
        assertFalse(stack.isEmpty());
    }

    @Test
    void testEmptyAfterClear() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.clear();

        assertTrue(stack.isEmpty());
    }

    @Test
    void testPushReturnsPushedItem() {
        CustomStack<String> stack = new CustomStack<>();

        String item = "apple";
        assertEquals(item, stack.push(item));
    }

    @Test
    void testPushIncreasesSizeAndMaintainsLifoOrder() {
        CustomStack<Integer> stack = new CustomStack<>();

        assertTrue(stack.isEmpty());

        stack.push(10);
        assertEquals(1, stack.size());
        assertEquals(10, stack.peek());

        stack.push(20);
        assertEquals(2, stack.size());
        assertEquals(20, stack.peek());

        stack.push(30);
        assertEquals(3, stack.size());
        assertEquals(30, stack.peek());
    }

    @Test
    void testPushNullElement() {
        CustomStack<String> stack = new CustomStack<>();

        assertNull(stack.push(null));
        assertEquals(1, stack.size());
        assertNull(stack.peek());
    }

        @Test
    void testPeekReturnsTopElementWithoutRemoving() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        assertEquals("banana", stack.peek());
        assertEquals(2, stack.size());
        assertEquals("banana", stack.peek());
    }

    @Test
    void testPeekWithNullTopElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        assertNull(stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void testPeekEmptyStackThrowsException() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertThrows(EmptyStackException.class, stack::peek);
    }

    @Test
    void testEmptyOnNewStack() {
        CustomStack<String> stack = new CustomStack<>();
        assertTrue(stack.empty());
    }

    @Test
    void testEmptyAfterPush() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        assertFalse(stack.empty());
    }

    @Test
    void testIndexOfFirstOccurrence() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("apple");

        assertEquals(0, stack.indexOf("apple"));
        assertEquals(1, stack.indexOf("banana"));
    }

    @Test
    void testIndexOfMiddleElement() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.add(30);

        assertEquals(1, stack.indexOf(20));
    }

    @Test
    void testIndexOfNonExistentElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(-1, stack.indexOf("cherry"));
    }

    @Test
    void testIndexOfOnEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        assertEquals(-1, stack.indexOf("apple"));
    }

    @Test
    void testIndexOfNullWhenPresent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");

        assertEquals(1, stack.indexOf(null));
    }

    @Test
    void testIndexOfNullWhenNotPresent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(-1, stack.indexOf(null));
    }

    @Test
    void testIndexOfWithFromIndexBasic() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("apple");
        stack.add("cherry");

        assertEquals(2, stack.indexOf("apple", 1));
        assertEquals(0, stack.indexOf("apple", 0));
    }

    @Test
    void testIndexOfWithFromIndexNotFoundBeforeStart() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(-1, stack.indexOf("apple", 1));
    }



    @Test
    void testIndexOfWithFromIndexAndNull() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");
        stack.add(null);

        assertEquals(3, stack.indexOf(null, 2));
        assertEquals(1, stack.indexOf(null, 0));
    }

    @Test
    void testIsEmptyAfterAdd() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        assertFalse(stack.isEmpty());
    }

    @Test
    void testIsEmptyAfterMultipleAdds() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        assertFalse(stack.isEmpty());
    }

    @Test
    void testIsEmptyAfterClear() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.clear();

        assertTrue(stack.isEmpty());
    }

    @Test
    void testLastIndexOfMultipleOccurrences() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("apple");
        stack.add("cherry");
        assertEquals(2, stack.lastIndexOf("apple"));
        assertEquals(1, stack.lastIndexOf("banana"));
    }

    @Test
    void testLastIndexOfSingleOccurrence() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.add(30);

        assertEquals(1, stack.lastIndexOf(20));
    }

    @Test
    void testLastIndexOfNonExistent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(-1, stack.lastIndexOf("cherry"));
    }

    @Test
    void testLastIndexOfOnEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        assertEquals(-1, stack.lastIndexOf("apple"));
    }

    @Test
    void testLastIndexOfNullWhenPresent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");
        stack.add(null);
        assertEquals(3, stack.lastIndexOf(null));
    }

    @Test
    void testLastIndexOfNullWhenNotPresent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(-1, stack.lastIndexOf(null));
    }

    @Test
    void testLastIndexOfWithFromIndexBasic() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("apple");
        stack.add("cherry");
        stack.add("apple");
        assertEquals(2, stack.lastIndexOf("apple", 3));
        assertEquals(0, stack.lastIndexOf("apple", 1));
    }

    @Test
    void testLastIndexOfWithFromIndexNegative() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        assertThrows(IndexOutOfBoundsException.class, () -> stack.lastIndexOf("apple", -1));
        assertThrows(IndexOutOfBoundsException.class, () -> stack.lastIndexOf("apple", -5));
    }

    @Test
    void testLastIndexOfWithFromIndexNonExistent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(-1, stack.lastIndexOf("cherry", 1));
    }

    @Test
    void testLastIndexOfWithFromIndexAndNull() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add(null);
        stack.add("apple");
        stack.add(null);
        stack.add("banana");
        assertEquals(2, stack.lastIndexOf(null, 2));
        assertEquals(0, stack.lastIndexOf(null, 1));
    }

    @Test
    void testPopReturnsAndRemovesTopElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(2, stack.size());
        assertEquals("banana", stack.pop());
        assertEquals(1, stack.size());
        assertEquals("apple", stack.peek());
    }

    @Test
    void testPopLifoOrderMultipleElements() {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.add(10);
        stack.add(20);
        stack.add(30);
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopNullElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        assertNull(stack.pop());
        assertEquals(1, stack.size());
        assertEquals("apple", stack.peek());
    }

    @Test
    void testPopEmptyStackThrowsException() {
        CustomStack<Integer> stack = new CustomStack<>();
        assertThrows(EmptyStackException.class, stack::pop);
    }


    @Test
    void testSearchTopElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(0, stack.search("banana"));
        assertEquals(1, stack.search("apple"));

    }

    @Test
    void testSearchBottomElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(1, stack.search("apple"));
    }

    @Test
    void testSearchNonExistentElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals(-1, stack.search("cherry"));
    }

    @Test
    void testSearchDuplicatesReturnsNearestToTop() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("apple");

        assertEquals(0, stack.search("apple"));
    }

    @Test
    void testSearchNullElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);

        assertEquals(0, stack.search(null));
    }

    @Test
    void testSearchEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();

        assertEquals(-1, stack.search("apple"));
    }

    @Test
    void testRemoveExistingElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        assertTrue(stack.remove("banana"));
        assertEquals(2, stack.size());

        assertEquals("apple", stack.get(0));
        assertEquals("cherry", stack.get(1));
    }

    @Test
    void testRemoveFirstOccurrenceOnlyWhenDuplicatesExist() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("apple");

        assertTrue(stack.remove("apple"));
        assertEquals(2, stack.size());

        assertEquals("apple", stack.get(0));
        assertEquals("banana", stack.get(1));
    }

    @Test
    void testRemoveNonExistentElementReturnsFalse() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertFalse(stack.remove("cherry"));
        assertEquals(2, stack.size());
    }

    @Test
    void testRemoveNullElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");

        assertTrue(stack.remove(null));
        assertEquals(2, stack.size());
        assertEquals(-1, stack.indexOf(null));
        assertEquals("banana", stack.get(1));
    }

    @Test
    void testRemoveFromEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        assertFalse(stack.remove("apple"));
        assertTrue(stack.isEmpty());
    }

    @Test
    void testRemoveAtIndexMiddle() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        assertEquals("banana", stack.remove(1));
        assertEquals(2, stack.size());
        assertEquals("apple", stack.get(0));
        assertEquals("cherry", stack.get(1));
    }

    @Test
    void testRemoveAtIndexFirst() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals("apple", stack.remove(0));
        assertEquals(1, stack.size());
        assertEquals("banana", stack.get(0));
    }

    @Test
    void testRemoveAtIndexLast() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals("banana", stack.remove(1));
        assertEquals(1, stack.size());
        assertEquals("apple", stack.get(0));
    }

    @Test
    void testRemoveAtIndexNullElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");

        assertNull(stack.remove(1));
        assertEquals(2, stack.size());
        assertEquals("banana", stack.get(1));
    }

    @Test
    void testRemoveAtIndexNegativeThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertThrows(IndexOutOfBoundsException.class, () -> stack.remove(-1));
    }

    @Test
    void testRemoveAtIndexTooLargeThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertThrows(IndexOutOfBoundsException.class, () -> stack.remove(2));
        assertThrows(IndexOutOfBoundsException.class, () -> stack.remove(5));
    }

    @Test
    void testRemoveFromEmptyStackThrowsException() {
        CustomStack<String> stack = new CustomStack<>();

        assertThrows(IndexOutOfBoundsException.class, () -> stack.remove(0));
    }

    @Test
    void testRemoveAllStandard() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");
        stack.add("banana");
        stack.add("date");

        List<String> toRemove = Arrays.asList("banana", "cherry");

        assertTrue(stack.removeAll(toRemove));
        assertEquals(2, stack.size());

        assertEquals("apple", stack.get(0));
        assertEquals("date", stack.get(1));
    }

    @Test
    void testRemoveAllNoMatches() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        List<String> toRemove = Arrays.asList("cherry", "date");

        assertFalse(stack.removeAll(toRemove));
        assertEquals(2, stack.size());
        assertEquals("apple", stack.get(0));
        assertEquals("banana", stack.get(1));
    }

    @Test
    void testRemoveAllEntireStack() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        List<String> toRemove = Arrays.asList("apple", "banana");

        assertTrue(stack.removeAll(toRemove));
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void testRemoveAllWithNullElements() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");
        stack.add(null);

        List<String> toRemove = Arrays.asList(null, "banana");

        assertTrue(stack.removeAll(toRemove));
        assertEquals(1, stack.size());
        assertEquals("apple", stack.get(0));
        assertEquals(-1, stack.indexOf(null));
    }

    @Test
    void testRemoveAllEmptyCollection() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertFalse(stack.removeAll(Collections.emptyList()));
        assertEquals(2, stack.size());
    }

    @Test
    void testRemoveAllFromEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        List<String> toRemove = Arrays.asList("apple", "banana");

        assertFalse(stack.removeAll(toRemove));
        assertTrue(stack.isEmpty());
    }

    @Test
    void testRemoveAllNullCollectionThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertThrows(NullPointerException.class, () -> stack.removeAll(null));
    }

    @Test
    void testRetainAllStandard() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");
        stack.add("banana");
        stack.add("date");

        List<String> toRetain = Arrays.asList("banana", "date");

        assertTrue(stack.retainAll(toRetain));
        assertEquals(3, stack.size());
        assertEquals("banana", stack.get(0));
        assertEquals("banana", stack.get(1));
        assertEquals("date", stack.get(2));
    }

    @Test
    void testRetainAllNoChanges() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        List<String> toRetain = Arrays.asList("apple", "banana", "cherry");

        assertFalse(stack.retainAll(toRetain));
        assertEquals(2, stack.size());
        assertEquals("apple", stack.get(0));
        assertEquals("banana", stack.get(1));
    }

    @Test
    void testRetainAllEmptyCollection() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertTrue(stack.retainAll(Collections.emptyList()));
        assertTrue(stack.isEmpty());
    }

    @Test
    void testRetainAllDisjoint() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        List<String> toRetain = Arrays.asList("cherry", "date");

        assertTrue(stack.retainAll(toRetain));
        assertTrue(stack.isEmpty());
    }

    @Test
    void testRetainAllWithNullElements() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("banana");
        stack.add(null);

        List<String> toRetain = Arrays.asList(null, "apple");

        assertTrue(stack.retainAll(toRetain));
        assertEquals(3, stack.size());
        assertEquals("apple", stack.get(0));
        assertNull(stack.get(1));
        assertNull(stack.get(2));
    }

    @Test
    void testRetainAllNullCollectionThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertThrows(NullPointerException.class, () -> stack.retainAll(null));
    }

    @Test
    void testSetStandard() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        assertEquals("banana", stack.set(1, "blueberry"));
        assertEquals(3, stack.size());
        assertEquals("apple", stack.get(0));
        assertEquals("blueberry", stack.get(1));
        assertEquals("cherry", stack.get(2));
    }

    @Test
    void testSetFirst() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals("apple", stack.set(0, "apricot"));
        assertEquals("apricot", stack.get(0));
    }

    @Test
    void testSetLast() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals("banana", stack.set(1, "blackberry"));
        assertEquals("blackberry", stack.get(1));
    }

    @Test
    void testSetWithNull() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        assertEquals("banana", stack.set(1, null));
        assertNull(stack.get(1));

        assertNull(stack.set(1, "banana"));
        assertEquals("banana", stack.get(1));
    }

    @Test
    void testSetNegativeIndexThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertThrows(IndexOutOfBoundsException.class, () -> stack.set(-1, "banana"));
    }

    @Test
    void testSetIndexEqualsSizeThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertThrows(IndexOutOfBoundsException.class, () -> stack.set(1, "banana"));
    }

    @Test
    void testSetIndexTooLargeThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertThrows(IndexOutOfBoundsException.class, () -> stack.set(5, "banana"));
    }

    @Test
    void testSetOnEmptyStackThrowsException() {
        CustomStack<String> stack = new CustomStack<>();

        assertThrows(IndexOutOfBoundsException.class, () -> stack.set(0, "banana"));
    }


    @Test
    void testEmptyAfterPushAndRemove() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.remove(0);
        assertTrue(stack.empty());
    }

    @Test
    void testEmptyMultipleElementsPartiallyRemoved() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.remove(1);
        assertFalse(stack.empty());
    }

    @Test
    void testSubListBasicExtraction() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");
        stack.add("date");

        List<String> sub = stack.subList(1, 3);
        assertEquals(2, sub.size());
        assertEquals("banana", sub.get(0));
        assertEquals("cherry", sub.get(1));
    }

    @Test
    void testSubListLiveViewModification() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        List<String> sub = stack.subList(1, 3);

        assertEquals("banana", sub.set(0, "blueberry"));
        assertEquals("blueberry", stack.get(1));
    }

    @Test
    void testSubListAddAndRemoveReflectsInParent() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("date");

        List<String> sub = stack.subList(1, 1);
        sub.add(0, "banana");
        sub.add(1, "cherry");

        assertEquals(4, stack.size());
        assertEquals("apple", stack.get(0));
        assertEquals("banana", stack.get(1));
        assertEquals("cherry", stack.get(2));
        assertEquals("date", stack.get(3));
        assertEquals("banana", sub.remove(0));
        assertEquals(3, stack.size());
        assertEquals("cherry", stack.get(1));
    }

    @Test
    void testSubListInvalidRangeThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        assertThrows(IndexOutOfBoundsException.class, () -> stack.subList(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> stack.subList(0, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> stack.subList(2, 1));
    }

    @Test
    void testNestedSubList() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("zero");
        stack.add("one");
        stack.add("two");
        stack.add("three");
        stack.add("four");

        List<String> sub1 = stack.subList(1, 4);
        List<String> sub2 = sub1.subList(1, 3);

        assertEquals(2, sub2.size());
        assertEquals("two", sub2.get(0));
        assertEquals("three", sub2.get(1));

        sub2.set(0, "updated");
        assertEquals("updated", stack.get(2));
    }

    @Test
    void testToArrayPopulatedStack() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        Object[] array = stack.toArray();

        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals("apple", array[0]);
        assertEquals("banana", array[1]);
        assertEquals("cherry", array[2]);
    }

    @Test
    void testToArrayReturnsIndependentCopy() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        Object[] array = stack.toArray();

        array[0] = "modified";
        assertEquals("apple", stack.get(0));

        stack.set(1, "blueberry");
        assertEquals("banana", array[1]);
    }

    @Test
    void testToArrayWithLargeEnoughArray() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        String[] target = new String[5];
        target[2] = "sentinel";

        String[] result = stack.toArray(target);

        assertSame(target, result, "Should return the exact same array instance if it's large enough");
        assertEquals("apple", result[0]);
        assertEquals("banana", result[1]);
        assertNull(result[2], "The element immediately following the end of the collection should be set to null");
    }

    @Test
    void testToArrayWithTooSmallArray() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        String[] target = new String[1];
        String[] result = stack.toArray(target);

        assertNotSame(target, result, "Should allocate a new array of the same runtime type if too small");
        assertEquals(3, result.length);
        assertEquals("apple", result[0]);
        assertEquals("banana", result[1]);
        assertEquals("cherry", result[2]);
    }

    @Test
    void testToArrayNullArgumentThrowsException() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertThrows(NullPointerException.class, () -> stack.toArray((String[]) null),
                "Passing a null array argument must throw a NullPointerException");
    }

    @Test
    void testToArrayEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        String[] target = new String[2];
        target[0] = "sentinel";

        String[] result = stack.toArray(target);

        assertSame(target, result);
        assertNull(result[0], "First element should be null since size is 0");
    }

    @Test
    void testToStringEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        assertEquals("[]", stack.toString());
    }

    @Test
    void testToStringSingleElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        assertEquals("[apple]", stack.toString());
    }

    @Test
    void testToStringMultipleElements() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");
        assertEquals("[apple, banana, cherry]", stack.toString());
    }

    @Test
    void testToStringWithNullElements() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("cherry");
        assertEquals("[apple, null, cherry]", stack.toString());
    }

    @Test
    void testEqualsSameInstance() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        assertEquals(stack, stack);
    }

    @Test
    void testEqualsWithNullAndDifferentType() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");

        assertNotEquals(null, stack);
        assertNotEquals(stack, "Not a list");
    }

    @Test
    void testEqualsDifferentSizes() {
        CustomStack<String> stack1 = new CustomStack<>();
        stack1.add("apple");

        CustomStack<String> stack2 = new CustomStack<>();
        stack2.add("apple");
        stack2.add("banana");

        assertNotEquals(stack1, stack2);
    }

    @Test
    void testEqualsEqualStacksAndLists() {
        CustomStack<String> stack1 = new CustomStack<>();
        stack1.add("apple");
        stack1.add("banana");

        CustomStack<String> stack2 = new CustomStack<>();
        stack2.add("apple");
        stack2.add("banana");

        assertEquals(stack1, stack2);
        assertEquals(stack2, stack1);

        List<String> arrayList = new ArrayList<>();
        arrayList.add("apple");
        arrayList.add("banana");
        assertEquals(stack1, arrayList);
        assertTrue(stack1.equals(arrayList));
    }

    @Test
    void testEqualsWithNullElements() {
        CustomStack<String> stack1 = new CustomStack<>();
        stack1.add("apple");
        stack1.add(null);

        CustomStack<String> stack2 = new CustomStack<>();
        stack2.add("apple");
        stack2.add(null);

        assertEquals(stack1, stack2);

        CustomStack<String> stack3 = new CustomStack<>();
        stack3.add("apple");
        stack3.add("banana");

        assertNotEquals(stack1, stack3);
    }

    @Test
    void testIteratorEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        Iterator<String> iterator = stack.iterator();

        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIteratorMultipleElements() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        Iterator<String> iterator = stack.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("apple", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("banana", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("cherry", iterator.next());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIteratorWithNullElements() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("cherry");

        Iterator<String> iterator = stack.iterator();

        assertEquals("apple", iterator.next());
        assertNull(iterator.next());
        assertEquals("cherry", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorSnapshotIsolation() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");

        Iterator<String> iterator = stack.iterator();
        stack.add("cherry");
        stack.set(0, "modified");

        assertEquals("apple", iterator.next());
        assertEquals("banana", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testListIteratorEmpty() {
        CustomStack<String> stack = new CustomStack<>();
        ListIterator<String> it = stack.listIterator();

        assertNotNull(it);
        assertFalse(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());
        assertThrows(NoSuchElementException.class, it::next);
        assertThrows(NoSuchElementException.class, it::previous);
    }

    @Test
    void testListIteratorForwardTraversal() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        ListIterator<String> it = stack.listIterator();

        assertTrue(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());

        assertEquals("apple", it.next());
        assertEquals(1, it.nextIndex());
        assertEquals(0, it.previousIndex());

        assertEquals("banana", it.next());
        assertEquals("cherry", it.next());

        assertFalse(it.hasNext());
        assertTrue(it.hasPrevious());
        assertEquals(3, it.nextIndex());
        assertEquals(2, it.previousIndex());
    }

    @Test
    void testListIteratorBackwardTraversal() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        ListIterator<String> it = stack.listIterator(stack.size());

        assertFalse(it.hasNext());
        assertTrue(it.hasPrevious());
        assertEquals(3, it.nextIndex());
        assertEquals(2, it.previousIndex());

        assertEquals("cherry", it.previous());
        assertEquals(2, it.nextIndex());
        assertEquals(1, it.previousIndex());

        assertEquals("banana", it.previous());
        assertEquals("apple", it.previous());

        assertFalse(it.hasPrevious());
        assertTrue(it.hasNext());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());
    }

    @Test
    void testListIteratorIndexOutOfBounds() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        assertThrows(IndexOutOfBoundsException.class, () -> stack.listIterator(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> stack.listIterator(3));
    }


    @Test
    void testSortNaturalOrder() {
        CustomStack<Integer> stack = new CustomStack<>(20);
        stack.add(5);
        stack.add(1);
        stack.add(4);
        stack.add(2);
        stack.add(3);

        stack.sort(Comparator.naturalOrder());

        assertEquals(5, stack.size());
        assertEquals(1, stack.get(0));
        assertEquals(2, stack.get(1));
        assertEquals(3, stack.get(2));
        assertEquals(4, stack.get(3));
        assertEquals(5, stack.get(4));
    }

    @Test
    void testSortCustomComparator() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("banana");
        stack.add("apple");
        stack.add("cherry");

        stack.sort(Comparator.reverseOrder());

        assertEquals(3, stack.size());
        assertEquals("cherry", stack.get(0));
        assertEquals("banana", stack.get(1));
        assertEquals("apple", stack.get(2));
    }

    @Test
    void testSortEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        assertDoesNotThrow(() -> stack.sort(Comparator.naturalOrder()));
        assertEquals(0, stack.size());
    }

    @Test
    void testSortSingleElement() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("only");

        stack.sort(Comparator.naturalOrder());

        assertEquals(1, stack.size());
        assertEquals("only", stack.get(0));
    }

    @Test
    void testHashCodeEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        List<String> equivalentList = List.of();

        assertEquals(equivalentList.hashCode(), stack.hashCode(),
                "Empty stack hashCode should match an empty list");
    }

    @Test
    void testHashCodePopulatedStack() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add("banana");
        stack.add("cherry");

        List<String> equivalentList = Arrays.asList("apple", "banana", "cherry");

        assertEquals(equivalentList.hashCode(), stack.hashCode(),
                "Populated stack hashCode should match an equivalent list");
    }

    @Test
    void testHashCodeWithNullElements() {
        CustomStack<String> stack = new CustomStack<>();
        stack.add("apple");
        stack.add(null);
        stack.add("cherry");

        List<String> equivalentList = Arrays.asList("apple", null, "cherry");

        assertEquals(equivalentList.hashCode(), stack.hashCode(),
                "Stack with null elements should match a list with null elements without throwing NPE");
    }

    @Test
    void testHashCodeContractWithEquals() {
        CustomStack<String> stack1 = new CustomStack<>();
        stack1.add("x");
        stack1.add("y");

        CustomStack<String> stack2 = new CustomStack<>();
        stack2.add("x");
        stack2.add("y");

        assertEquals(stack1, stack2);
        assertEquals(stack1.hashCode(), stack2.hashCode(),
                "Equal stacks must produce identical hash codes");
    }

    @Test
    void testClonePopulatedStack() {
        CustomStack<String> original = new CustomStack<>();
        original.add("apple");
        original.add("banana");
        original.add("cherry");

        CustomStack<String> copy = original.clone();

        assertNotNull(copy);
        assertNotSame(original, copy, "Clone must be a separate instance");
        assertEquals(original.size(), copy.size());
        assertEquals(original, copy, "Clone should equal the original stack");

        assertEquals("apple", copy.get(0));
        assertEquals("banana", copy.get(1));
        assertEquals("cherry", copy.get(2));
    }

    @Test
    void testCloneWithNullElements() {
        CustomStack<String> original = new CustomStack<>();
        original.add("apple");
        original.add(null);
        original.add("cherry");

        CustomStack<String> copy = original.clone();

        assertEquals(original.size(), copy.size());
        assertEquals("apple", copy.get(0));
        assertNull(copy.get(1));
        assertEquals("cherry", copy.get(2));
        assertEquals(original, copy);
    }

    @Test
    void testCloneIndependence() {
        CustomStack<String> original = new CustomStack<>();
        original.add("apple");
        original.add("banana");

        CustomStack<String> copy = original.clone();

        original.add("cherry");
        original.set(0, "apricot");

        assertEquals(2, copy.size());
        assertEquals("apple", copy.get(0));
        assertEquals("banana", copy.get(1));
        assertNotEquals(original, copy);
    }

    @Test
    void testSpliteratorEmptyStack() {
        CustomStack<String> stack = new CustomStack<>();
        Spliterator<String> spliterator = stack.spliterator();

        assertNotNull(spliterator, "Spliterator must not be null");
        assertEquals(0, spliterator.estimateSize());
        assertTrue(spliterator.hasCharacteristics(Spliterator.SIZED));
        assertTrue(spliterator.hasCharacteristics(Spliterator.ORDERED));

        assertFalse(spliterator.tryAdvance(e -> fail("Should not advance on empty stack")));
    }

    @Test
    void testSpliteratorTraversalAndOrder() {
        CustomStack<String> stack = new CustomStack<>();

        stack.push("apple");
        stack.push("banana");
        stack.push("cherry");

        Spliterator<String> spliterator = stack.spliterator();

        assertEquals(3, spliterator.estimateSize());

        List<String> collected = new ArrayList<>();
        spliterator.forEachRemaining(collected::add);
        assertEquals(List.of("apple", "banana", "cherry"), collected);
    }

    @Test
    void testSpliteratorTryAdvance() {
        CustomStack<String> stack = new CustomStack<>();
        stack.push("one");
        stack.push("two");

        Spliterator<String> spliterator = stack.spliterator();
        List<String> results = new ArrayList<>();

        assertTrue(spliterator.tryAdvance(results::add));
        assertEquals(1, spliterator.estimateSize());

        assertTrue(spliterator.tryAdvance(results::add));
        assertEquals(0, spliterator.estimateSize());

        assertFalse(spliterator.tryAdvance(results::add));
        assertEquals(List.of("one", "two"), results);
    }

    @Test
    void testSpliteratorCharacteristics() {
        CustomStack<String> stack = new CustomStack<>();

        stack.push("item");
        Spliterator<String> spliterator = stack.spliterator();

        int expectedCharacteristics = Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE;
        assertEquals(expectedCharacteristics, spliterator.characteristics() & expectedCharacteristics);
    }

    @Test
    void testSpliteratorTrySplit() {
        CustomStack<String> stack = new CustomStack<>();

        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");

        Spliterator<String> spliterator1 = stack.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();

        if (spliterator2 != null) {
            List<String> part1 = new ArrayList<>();
            List<String> part2 = new ArrayList<>();

            spliterator1.forEachRemaining(part1::add);
            spliterator2.forEachRemaining(part2::add);

            List<String> combined = new ArrayList<>();
            combined.addAll(part2);
            combined.addAll(part1);

            assertEquals(4, combined.size());
            assertTrue(combined.containsAll(List.of("a", "b", "c", "d")));
        }
    }

    @Test
    void testIntegrationWithJavaStreams() {
        CustomStack<String> stack = new CustomStack<>();

        stack.push("java");
        stack.push("python");
        stack.push("rust");

        List<String> upperCaseList = StreamSupport.stream(stack.spliterator(), false)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        assertEquals(List.of("JAVA", "PYTHON", "RUST"), upperCaseList);
    }
}