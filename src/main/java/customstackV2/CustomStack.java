package customstackV2;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;

import static java.util.Objects.checkFromToIndex;
import static java.util.Objects.checkIndex;
import static java.util.Objects.requireNonNull;

/**
 * A resizable, indexable, last-in-first-out (LIFO) stack backed by a singly
 * linked chain of {@link Node} objects.
 *
 * <p>Unlike {@link java.util.Stack}, which extends {@link java.util.Vector}
 * and is backed by an array, {@code CustomStack} is backed by a linked list
 * in which each node holds a reference to the node below it (its
 * {@code previous} node). The most recently pushed element is the
 * {@code head} of the chain and represents the top of the stack.
 *
 * <p>Elements are also addressable by index, where index {@code 0} is the
 * element at the <em>bottom</em> of the stack (the first one pushed that is
 * still present) and index {@code size() - 1} is the element at the
 * <em>top</em> of the stack (the {@code head}). This means the class can be
 * used both with classic stack operations ({@link #push(Object)},
 * {@link #pop()}, {@link #peek()}) and with list-style, index-based
 * operations ({@link #get(int)}, {@link #set(int, Object)},
 * {@link #add(int, Object)}, {@link #remove(int)}, etc.), similar to
 * {@link java.util.List}.
 *
 * <p>Most mutating operations increment an internal {@code modCount}
 * counter. Views returned by {@link #subList(int, int)} (and, transitively,
 * {@link #listIterator()}) are fail-fast: if the stack is structurally
 * modified after such a view is created, other than through the view
 * itself, the view will throw a {@link ConcurrentModificationException} on
 * subsequent use. The {@link #iterator()} and {@link #spliterator()},
 * however, operate over a snapshot array taken at the time they are created
 * and are therefore not affected by later modifications to the stack.
 *
 * <p>This class permits {@code null} elements.
 *
 * <p><b>This implementation is not synchronized.</b> If multiple threads
 * access an instance concurrently, and at least one thread modifies it
 * structurally, it must be synchronized externally.
 *
 * @param <E> the type of elements held in this stack
 * @author Benjamin Kane
 * @see <a href="https://www.linkedin.com/in/benjamin-kane-81149482/">LinkedIn</a>
 * @see <a href="https://github.com/bk10aao">GitHub account bk10aao</a>
 * @see <a href="https://github.com/bk10aao/CustomStackV2>Repository</a>
 */
public class CustomStack<E> implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The node at the top of the stack (the most recently added element),
     * or {@code null} if the stack is empty. Each node's {@code previous}
     * reference points toward the bottom of the stack.
     */
    private Node<E> head;

    /**
     * The number of elements currently contained in this stack.
     */
    private int size = 0;

    /**
     * The number of times this stack has been structurally modified.
     * Used by fail-fast views such as {@link SubListView} to detect
     * concurrent or unexpected modification.
     */
    private int modCount = 0;

    /**
     * Constructs an empty stack.
     */
    public CustomStack() {
        head = null;
    }

    /**
     * Constructs a stack containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator. The last element produced by the iterator ends up at the
     * top of the stack.
     *
     * @param c the collection whose elements are to be placed into this stack
     */
    public CustomStack(final Collection<? extends E> c) {
        addAll(c);
    }

    /**
     * Constructs an empty stack with the specified initial capacity hint.
     * Because this stack is backed by a linked list rather than an array,
     * the capacity is not actually reserved; the parameter exists purely
     * for API compatibility and validation purposes.
     *
     * @param initialCapacity the initial capacity hint; must not be negative
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     */
    public CustomStack(final int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException();
        head = null;
    }

    /**
     * Pushes the specified element onto the top of this stack. This is
     * equivalent to {@link #push(Object)} except for the return type.
     *
     * @param e the element to add
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(final E e) {
        Node<E> newNode = new Node<>(e);
        if (head != null)
            newNode.previous = head;
        head = newNode;
        size++;
        modCount++;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this
     * stack, shifting the element currently at that position (if any) and
     * any subsequent elements one position toward the bottom of the stack.
     * Inserting at {@code index == size()} is equivalent to
     * {@link #add(Object)} (i.e. pushing the element).
     *
     * @param index   the index at which the element is to be inserted
     * @param element the element to insert
     * @return {@code true}
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index > size()})
     */
    public boolean add(final int index, final E element) {
        checkIndex(index, size + 1);
        if (index == size)
            add(element);
        else if (index == 0)
            pushToStart(element);
        else
            insert(index, element);
        return true;
    }

    /**
     * Pushes all the elements in the specified collection onto this stack,
     * in the order they are returned by the collection's iterator. The
     * last element produced by the iterator ends up at the top of the
     * stack.
     *
     * @param c collection containing elements to be added to this stack
     * @return {@code true} if this stack changed as a result of the call
     *         (i.e. {@code c} was non-empty)
     * @throws NullPointerException     if {@code c} is {@code null}
     * @throws IllegalArgumentException if {@code c} is this stack itself
     */
    public boolean addAll(final Collection<? extends E> c) {
        requireNonNull(c);
        if (c.equals(this))
            throw new IllegalArgumentException();
        if (c.isEmpty())
            return false;
        for (E e : c)
            add(e);
        return true;
    }

    /**
     * Inserts all the elements in the specified collection into this
     * stack, starting at the specified position, preserving the order in
     * which they are returned by the collection's iterator.
     *
     * @param index the index at which to insert the first element from
     *              the specified collection
     * @param c     collection containing elements to be added to this stack
     * @return {@code true} if this stack changed as a result of the call
     *         (i.e. {@code c} was non-empty)
     * @throws NullPointerException      if {@code c} is {@code null}
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index > size()})
     */
    public boolean addAll(final int index, final Collection<? extends E> c) {
        requireNonNull(c);
        checkIndex(index, size + 1);
        if (c.isEmpty())
            return false;
        Object[] elements = c.toArray();
        if (index == size) {
            for (int i = 0; i < elements.length; i++) {
                Node<E> newNode = new Node<>((E) elements[i]);
                newNode.previous = head;
                head = newNode;
            }
            size += c.size();
            modCount += c.size();
            return true;
        }
        return insertCollection(index, c, elements);
    }

    /**
     * Removes all the elements from this stack. The stack will be empty
     * after this call returns.
     */
    public void clear() {
        head = null;
        size = 0;
        modCount++;
    }

    /**
     * Returns a shallow copy of this stack: the elements themselves are
     * not copied, but a new backing chain of nodes is created containing
     * the same elements, in the same order.
     *
     * @return a clone of this stack
     */
    @Override
    public CustomStack<E> clone() {
        CustomStack<E> copy = new CustomStack<>();
        copy.addAll(subList(0, size));
        return copy;
    }

    /**
     * Returns {@code true} if this stack contains the specified value,
     * comparing elements using {@link Objects#equals(Object, Object)}.
     *
     * @param value the value whose presence in this stack is to be tested
     * @return {@code true} if this stack contains the specified value
     */
    public boolean contains(final E value) {
        if (head == null)
            return false;
        Node<E> current = head;
        while (current != null) {
            if (Objects.equals(value, current.value))
                return true;
            current = current.previous;
        }
        return false;
    }

    /**
     * Returns {@code true} if this stack contains all the elements of the
     * specified collection.
     *
     * @param c the collection to be checked for containment in this stack
     * @return {@code true} if this stack contains all the elements of the
     *         specified collection
     * @throws NullPointerException if {@code c} is {@code null}
     */
    public boolean containsAll(final Collection<?> c) {
        requireNonNull(c);
        if (c.isEmpty())
            return true;
        Set<?> values = (c instanceof Set) ? (Set<?>) c : new HashSet<>(c);
        Node<E> current = head;
        while (current != null) {
            values.remove(current.value);
            if (values.isEmpty())
                return true;
            current = current.previous;
        }
        return false;
    }

    /**
     * Tests if this stack has no elements. Semantically equivalent to
     * {@link #isEmpty()}; provided for API compatibility with
     * {@link java.util.Stack#empty()}.
     *
     * @return {@code true} if and only if this stack contains no elements
     */
    public boolean empty() {
        return size == 0;
    }

    /**
     * Compares the specified object with this stack for equality. Returns
     * {@code true} if the given object is also a {@code CustomStack} or a
     * {@link List} of the same size, containing equal elements in the same
     * (index) order.
     *
     * @param o the object to be compared for equality with this stack
     * @return {@code true} if the specified object is equal to this stack
     */
    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        int otherSize;
        IntFunction<Object> elementGetter;
        if (o instanceof CustomStack<?> otherStack) {
            otherSize = otherStack.size();
            elementGetter = otherStack::get;
        } else if (o instanceof List<?> otherList) {
            otherSize = otherList.size();
            elementGetter = otherList::get;
        } else
            return false;
        if (size != otherSize)
            return false;
        for (int i = 0; i < size; i++)
            if (!Objects.equals(get(i), elementGetter.apply(i)))
                return false;
        return true;
    }

    /**
     * Returns the element at the specified position in this stack, where
     * position {@code size() - 1} is the top of the stack and position
     * {@code 0} is the bottom.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this stack
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index >= size()})
     */
    public E get(final int index) {
        checkIndex(index, size);
        return getNode(index).value;
    }

    /**
     * Returns a hash code value for this stack, computed by combining the
     * hash codes of its elements in index order (bottom to top).
     *
     * @return the hash code value for this stack
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Object obj : toArray())
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        return hashCode;
    }

    /**
     * Returns the index of the first occurrence (i.e. the lowest index) of
     * the specified object in this stack, or {@code -1} if this stack does
     * not contain the object.
     *
     * @param o the object to search for
     * @return the index of the first occurrence of the specified object,
     *         or {@code -1} if it is not found
     */
    public int indexOf(final Object o) {
        if (head == null)
            return -1;
        return indexOf(o, 0);
    }

    /**
     * Returns the index of the first occurrence of the specified object in
     * this stack at or after the specified starting index, or {@code -1}
     * if no such occurrence exists.
     *
     * @param o     the object to search for
     * @param index the index to start searching from (inclusive)
     * @return the index of the first matching occurrence at or after
     *         {@code index}, or {@code -1} if none is found
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index >= size()})
     */
    public int indexOf(final Object o, final int index) {
        checkIndex(index, size);
        if (head == null)
            return -1;
        Node<E> current = head;
        int currentIndex = size - 1;
        int firstMatchIndex = -1;
        while (current != null) {
            if (currentIndex >= index && Objects.equals(o, current.value))
                firstMatchIndex = currentIndex;
            current = current.previous;
            currentIndex--;
        }
        return firstMatchIndex;
    }

    /**
     * Returns the index of the last occurrence (i.e. the highest index) of
     * the specified object in this stack, or {@code -1} if this stack does
     * not contain the object.
     *
     * @param o the object to search for
     * @return the index of the last occurrence of the specified object,
     *         or {@code -1} if it is not found
     */
    public int lastIndexOf(final Object o) {
        if (isEmpty())
            return -1;
        return lastIndexOf(o, size - 1);
    }

    /**
     * Returns the index of the last occurrence of the specified object in
     * this stack, searching backward starting at (and including) the
     * specified index, or {@code -1} if no such occurrence exists.
     *
     * @param o     the object to search for
     * @param index the index to start searching backward from (inclusive)
     * @return the index of the matching occurrence found, or {@code -1} if
     *         none is found
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index >= size()})
     */
    public int lastIndexOf(final Object o, final int index) {
        checkIndex(index, size);
        Node<E> current = head;
        int currentIndex = size;
        while (currentIndex > index + 1) {
            current = current.previous;
            currentIndex--;
        }
        int searchIndex = index;
        while (current != null) {
            if (Objects.equals(o, current.value))
                return searchIndex;
            current = current.previous;
            searchIndex--;
        }
        return -1;
    }

    /**
     * Returns {@code true} if this stack contains no elements.
     *
     * @return {@code true} if this stack contains no elements
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns an iterator over the elements in this stack, in index order
     * (bottom to top). The iterator operates over a snapshot of the
     * stack's contents taken at the time this method is called; it does
     * not reflect subsequent modifications to the stack and does not
     * support {@link Iterator#remove()}.
     *
     * @return an iterator over the elements in this stack
     */
    public Iterator<E> iterator() {
        Object[] snapshot = toArray();
        return new Iterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < snapshot.length;
            }

            @Override
            public E next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return (E) snapshot[cursor++];
            }
        };
    }

    /**
     * Returns a fail-fast list iterator over all the elements in this
     * stack (in index order), starting at index {@code 0}. The returned
     * iterator is backed by a {@link #subList(int, int)} view of this
     * stack and supports element replacement, insertion, and removal
     * through the iterator.
     *
     * @return a list iterator over the elements in this stack
     */
    public ListIterator<E> listIterator() {
        checkIndex(0, size + 1);
        return subList(0, size).listIterator();
    }

    /**
     * Returns a fail-fast list iterator over all the elements in this
     * stack (in index order), starting at the specified position.
     *
     * @param index the index of the first element to be returned from the
     *              list iterator
     * @return a list iterator over the elements in this stack, starting
     *         at the specified position
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index > size()})
     */
    public ListIterator<E> listIterator(final int index) {
        checkIndex(index, size + 1);
        return subList(0, size).listIterator(index);
    }

    /**
     * Looks at the element at the top of this stack without removing it.
     *
     * @return the element at the top of this stack
     * @throws EmptyStackException if this stack is empty
     */
    public E peek() {
        if (head == null)
            throw new EmptyStackException();
        return head.value;
    }

    /**
     * Removes and returns the element at the top of this stack.
     *
     * @return the element previously at the top of this stack
     * @throws EmptyStackException if this stack is empty
     */
    public E pop() {
        if (size == 0)
            throw new EmptyStackException();
        E item = head.value;
        head = head.previous;
        size--;
        modCount++;
        return item;
    }

    /**
     * Pushes an element onto the top of this stack.
     *
     * @param item the element to push
     * @return the {@code item} argument, for call chaining
     */
    public E push(final E item) {
        add(item);
        return item;
    }

    /**
     * Removes the element at the specified position in this stack,
     * shifting any subsequent elements one position toward the top of the
     * stack, and returns the removed element.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index >= size()})
     */
    public E remove(final int index) {
        checkIndex(index, size);
        if (index == size - 1) {
            E value = head.value;
            head = head.previous;
            size--;
            modCount++;
            return value;
        }
        return removeInnerIndex(index);
    }

    /**
     * Removes the first occurrence of the specified element from this
     * stack, if it is present, comparing elements using
     * {@link Objects#equals(Object, Object)}.
     *
     * @param o the element to be removed from this stack, if present
     * @return {@code true} if this stack contained the specified element
     */
    public boolean remove(final Object o) {
        if (head == null)
            return false;
        if (Objects.equals(o, head.value)) {
            head = head.previous;
            size--;
            modCount++;
            return true;
        }
        Node<E> previous = head;
        Node<E> current = head.previous;
        while (current != null) {
            if (Objects.equals(o, current.value)) {
                previous.previous = current.previous;
                size--;
                modCount++;
                return true;
            }
            previous = previous.previous;
            current = current.previous;
        }
        return false;
    }

    /**
     * Removes from this stack all of its elements that are contained in
     * the specified collection.
     *
     * @param c collection containing elements to be removed from this stack
     * @return {@code true} if this stack changed as a result of the call
     * @throws NullPointerException if {@code c} is {@code null}
     */
    public boolean removeAll(final Collection<?> c) {
        requireNonNull(c);
        if (head == null)
            return false;
        Set<?> values = (c instanceof Set) ? (Set<?>) c : new HashSet<>(c);
        boolean modified = false;
        while (head != null && values.contains(head.value)) {
            head = head.previous;
            size--;
            modCount++;
            modified = true;
        }
        if (head == null)
            return modified;
        Node<E> previous = head;
        Node<E> current = head.previous;
        while (current != null) {
            if (values.contains(current.value)) {
                previous.previous = current.previous;
                size--;
                modCount++;
                modified = true;
                current = previous.previous;
            } else {
                previous = current;
                current = current.previous;
            }
        }
        return modified;
    }

    /**
     * Retains only the elements in this stack that are contained in the
     * specified collection, removing all others.
     *
     * @param c collection containing elements to be retained in this stack
     * @return {@code true} if this stack changed as a result of the call
     * @throws NullPointerException if {@code c} is {@code null}
     */
    public boolean retainAll(final Collection<?> c) {
        requireNonNull(c);
        if (head == null)
            return false;
        Set<?> values = (c instanceof Set) ? (Set<?>) c : new HashSet<>(c);
        boolean modified = false;
        while (head != null && !values.contains(head.value)) {
            head = head.previous;
            size--;
            modCount++;
            modified = true;
        }
        if (head == null)
            return modified;
        Node<E> previous = head;
        Node<E> current = head.previous;
        while (current != null)
            if (!values.contains(current.value)) {
                previous.previous = current.previous;
                size--;
                modCount++;
                modified = true;
                current = previous.previous;
            } else {
                previous = current;
                current = current.previous;
            }
        return modified;
    }

    /**
     * Returns the distance from the top of the stack to the first
     * occurrence of the specified object, where the top element itself is
     * at distance {@code 0}. Returns {@code -1} if the object is not
     * found.
     *
     * <p><b>Note:</b> unlike {@link java.util.Stack#search(Object)}, which
     * is 1-indexed from the top, this method is 0-indexed: the top
     * element returns {@code 0} rather than {@code 1}.
     *
     * @param o the desired object
     * @return the 0-based distance from the top of the stack to the
     *         object, or {@code -1} if the object is not found
     */
    public int search(final Object o) {
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (Objects.equals(o, current.value))
                return index;
            current = current.previous;
            index++;
        }
        return -1;
    }

    /**
     * Replaces the element at the specified position in this stack with
     * the specified element.
     *
     * @param index   the index of the element to replace
     * @param element the element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     *                                    ({@code index < 0 || index >= size()})
     */
    public E set(final int index, final E element) {
        checkIndex(index, size);
        Node<E> node = getNode(index);
        E oldValue = node.value;
        node.value = element;
        modCount++;
        return oldValue;
    }

    /**
     * Returns the number of elements in this stack.
     *
     * @return the number of elements in this stack
     */
    public int size() {
        return size;
    }

    /**
     * Sorts the elements of this stack according to the order induced by
     * the specified comparator. After sorting, elements are arranged in
     * ascending order by index, meaning the element considered "greatest"
     * by the comparator ends up at the top of the stack (the new
     * {@code head}), and the "least" element ends up at index {@code 0}.
     *
     * @param c the comparator used to compare stack elements
     */
    public void sort(final Comparator<? super E> c) {
        if (size <= 1)
            return;
        Object[] array = toArray();
        Arrays.sort(array, (Comparator<Object>) c);
        Node<E> current = head;
        for (int i = size - 1; i >= 0; i--) {
            current.value = (E) array[i];
            current = current.previous;
        }
        modCount++;
    }

    /**
     * Returns a {@link Spliterator} over the elements in this stack, in
     * index order (bottom to top). The spliterator operates over a
     * snapshot of the stack's contents taken at the time this method is
     * called, reports the {@link Spliterator#ORDERED}, {@link
     * Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, and {@link
     * Spliterator#IMMUTABLE} characteristics, and does not support
     * splitting.
     *
     * @return a spliterator over the elements in this stack
     */
    public Spliterator<E> spliterator() {
        Object[] snapshot = toArray();
        return new Spliterator<>() {
            private int index = 0;

            @Override
            public boolean tryAdvance(Consumer<? super E> action) {
                requireNonNull(action);
                if (index < snapshot.length) {
                    E item = (E) snapshot[index++];
                    action.accept(item);
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<E> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return snapshot.length - index;
            }

            @Override
            public int characteristics() {
                return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE;
            }
        };
    }

    /**
     * Returns a view of the portion of this stack between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive. The
     * returned list is backed by this stack, is fail-fast (it detects
     * structural modification of the backing stack made other than
     * through the view itself), and supports {@code get}, {@code set},
     * {@code add}, and {@code remove}.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this stack
     * @throws IndexOutOfBoundsException if the range is out of bounds
     */
    public List<E> subList(final int fromIndex, final int toIndex) {
        checkFromToIndex(fromIndex, toIndex, size);
        return new SubListView(fromIndex, toIndex);
    }

    /**
     * Returns an array containing all the elements in this stack, in
     * index order (bottom to top).
     *
     * @return an array containing all the elements in this stack
     */
    public Object[] toArray() {
        return subList(0, size).toArray();
    }

    /**
     * Returns an array containing all the elements in this stack, in
     * index order (bottom to top); the runtime type of the returned array
     * is that of the specified array.
     *
     * @param a   the array into which the elements of this stack are to
     *            be stored, if it is big enough; otherwise, a new array
     *            of the same runtime type is allocated for this purpose
     * @param <T> the runtime type of the array to contain the collection
     * @return an array containing the elements of this stack
     */
    public <T> T[] toArray(final T[] a) {
        return subList(0, size).toArray(a);
    }

    /**
     * Returns a string representation of this stack, consisting of the
     * elements' string representations in index order (bottom to top),
     * enclosed in square brackets and separated by {@code ", "}, as
     * produced by {@link Arrays#toString(Object[])}.
     *
     * @return a string representation of this stack
     */
    @Override
    public String toString() {
        return Arrays.toString(subList(0, size).toArray());
    }

    /**
     * Returns the node at the specified index by walking backward from
     * the {@code head}.
     *
     * @param index the index of the node to retrieve
     * @return the node at the specified index
     */
    private Node<E> getNode(final int index) {
        Node<E> current = head;
        int currentIndex = size;
        while (currentIndex > index + 1) {
            current = current.previous;
            currentIndex--;
        }
        return current;
    }

    /**
     * Inserts a new node holding {@code e} at the specified index, where
     * {@code 0 < index < size}. Handling of the boundary indices
     * {@code 0} and {@code size} is done by {@link #pushToStart(Object)}
     * and {@link #add(Object)} respectively.
     *
     * @param index the index at which to insert the new node
     * @param e     the element to insert
     */
    private void insert(final int index, final E e) {
        Node<E> current = head;
        int currentIndex = size - 1;
        while (currentIndex > index) {
            current = current.previous;
            currentIndex--;
        }
        Node<E> newNode = new Node<>(e);
        newNode.previous = current.previous;
        current.previous = newNode;
        size++;
        modCount++;
    }

    /**
     * Inserts the given elements as a contiguous run of new nodes starting
     * at the specified index, preserving their iteration order. Used to
     * implement {@link #addAll(int, Collection)} for indices other than
     * {@code size}.
     *
     * @param index    the index at which to begin insertion
     * @param c        the source collection, used only for its size
     * @param elements the elements of {@code c}, as an array, in
     *                 iteration order
     * @return {@code true}
     */
    private boolean insertCollection(int index, Collection<? extends E> c, Object[] elements) {
        Node<E> current = head;
        int currentIndex = size;
        while (currentIndex > index + 1) {
            current = current.previous;
            currentIndex--;
        }
        Node<E> previous = current.previous;
        for (int i = elements.length - 1; i >= 0; i--) {
            current.previous = new Node<>((E) elements[i]);
            current = current.previous;
        }
        current.previous = previous;
        size += c.size();
        modCount += c.size();
        return true;
    }

    /**
     * Inserts the given item at index {@code 0}, i.e. at the bottom of
     * the stack, by walking to the end of the chain and appending a new
     * node there.
     *
     * @param item the element to insert at the bottom of the stack
     */
    private void pushToStart(E item) {
        Node<E> newNode = new Node<>(item);
        if (head == null)
            head = newNode;
        else {
            Node<E> current = head;
            while (current.previous != null)
                current = current.previous;
            current.previous = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * Removes and returns the value of the node at the specified index,
     * where {@code index} is not the index of the {@code head} node (that
     * case is handled directly by {@link #remove(int)}).
     *
     * @param index the index of the node to remove
     * @return the value that was held by the removed node
     */
    private E removeInnerIndex(int index) {
        Node<E> previous = head;
        for (int i = size - 1; i > index + 1; i--)
            previous = previous.previous;
        Node<E> current = previous.previous;
        E value = current.value;
        previous.previous = current.previous;
        size--;
        modCount++;
        return value;
    }

    /**
     * A single node in the singly linked chain backing this queue,
     * holding an element value and a reference to the previous node.
     *
     * @param <E> the type of the value held by this node
     */
    private static class Node<E> implements java.io.Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * Reference to the previous node in the chain, or {@code null} if this is the first node.
         */
        private Node<E> previous;

        /**
         * The element value held by this node.
         */
        private E value;

        /**
         * Constructs a new node holding the specified value, with no
         * successor.
         *
         * @param value the element value to store in this node
         */
        public Node(E value) {
            this.value = value;
        }
    }

    /**
     * A fail-fast {@link List} view over a contiguous range of indices of
     * the enclosing {@link CustomStack}. Reads and writes performed
     * through this view are delegated back to the enclosing stack with
     * the appropriate index offset applied, and structural changes made
     * to the enclosing stack outside of this view are detected via
     * {@link CustomStack#modCount} and reported as a
     * {@link ConcurrentModificationException}.
     */
    private class SubListView extends java.util.AbstractList<E> {

        /**
         * The index into the enclosing stack corresponding to index
         * {@code 0} of this view.
         */
        private final int offset;

        /**
         * The number of elements currently visible through this view.
         */
        private int size;

        /**
         * The {@code modCount} of the enclosing stack expected by this
         * view; used to detect unexpected structural modification.
         */
        private int expectedModCount;

        /**
         * Constructs a view over the range {@code [fromIndex, toIndex)}
         * of the enclosing stack.
         *
         * @param fromIndex low endpoint (inclusive) of the view
         * @param toIndex   high endpoint (exclusive) of the view
         */
        SubListView(final int fromIndex, final int toIndex) {
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
            this.expectedModCount = CustomStack.this.modCount;
        }

        /**
         * Inserts the specified element at the specified position in this
         * view, delegating to the enclosing stack with the view's offset
         * applied.
         *
         * @param index   the index at which to insert, relative to this view
         * @param element the element to insert
         * @throws ConcurrentModificationException if the enclosing stack
         *                                          was structurally modified
         *                                          outside of this view
         */
        public void add(final int index, final E element) {
            checkForStructuralChange();
            checkIndex(index, size + 1);
            CustomStack.this.add(offset + index, element);
            size++;
            expectedModCount = CustomStack.this.modCount;
        }

        /**
         * Returns the element at the specified position in this view.
         *
         * @param index the index of the element to return, relative to
         *              this view
         * @return the element at the specified position
         * @throws ConcurrentModificationException if the enclosing stack
         *                                          was structurally modified
         *                                          outside of this view
         */
        public E get(final int index) {
            checkForStructuralChange();
            checkIndex(index, size);
            return CustomStack.this.get(offset + index);
        }

        /**
         * Removes the element at the specified position in this view,
         * delegating to the enclosing stack with the view's offset
         * applied.
         *
         * @param index the index of the element to remove, relative to
         *              this view
         * @return the element previously at the specified position
         * @throws ConcurrentModificationException if the enclosing stack
         *                                          was structurally modified
         *                                          outside of this view
         */
        public E remove(final int index) {
            checkForStructuralChange();
            checkIndex(index, size);
            E removed = CustomStack.this.remove(offset + index);
            size--;
            expectedModCount = CustomStack.this.modCount;
            return removed;
        }

        /**
         * Replaces the element at the specified position in this view
         * with the specified element.
         *
         * @param index   the index of the element to replace, relative to
         *                this view
         * @param element the element to be stored at the specified position
         * @return the element previously at the specified position
         * @throws ConcurrentModificationException if the enclosing stack
         *                                          was structurally modified
         *                                          outside of this view
         */
        public E set(final int index, final E element) {
            checkForStructuralChange();
            checkIndex(index, size);
            E old = CustomStack.this.set(offset + index, element);
            expectedModCount = CustomStack.this.modCount;
            return old;
        }

        /**
         * Returns the number of elements currently visible through this
         * view.
         *
         * @return the size of this view
         * @throws ConcurrentModificationException if the enclosing stack
         *                                          was structurally modified
         *                                          outside of this view
         */
        public int size() {
            checkForStructuralChange();
            return size;
        }

        /**
         * Checks whether the enclosing stack has been structurally
         * modified since this view was created or last updated, and
         * throws if so.
         *
         * @throws ConcurrentModificationException if the enclosing stack's
         *                                          {@code modCount} no
         *                                          longer matches the
         *                                          expected value
         */
        private void checkForStructuralChange() {
            if (CustomStack.this.modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
