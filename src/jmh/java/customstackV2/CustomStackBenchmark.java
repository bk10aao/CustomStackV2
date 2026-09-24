package customstackV2;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class CustomStackBenchmark {

    @Param({"10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    public int size;

    private List<Integer> sourceCollection;
    private CustomStack<Integer> stack;
    private List<Integer> subCollection;
    private List<Integer> removeCollection;

    @Setup(Level.Trial)
    public void setupTrial() {
        sourceCollection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            sourceCollection.add(i);
        }

        subCollection = new ArrayList<>(Math.max(1, size / 10));
        for (int i = 0; i < Math.max(1, size / 10); i++) {
            subCollection.add(i);
        }

        removeCollection = new ArrayList<>(size / 5);
        for (int i = 0; i < size / 5; i++) {
            removeCollection.add(i);
        }
    }

    @Setup(Level.Invocation)
    public void setupInvocation() {
        stack = new CustomStack<>();
        for (int i = 0; i < size; i++) {
            stack.push(i);
        }
    }

    @Benchmark
    public CustomStack<Integer> testConstructorDefault() {
        return new CustomStack<>();
    }

    @Benchmark
    public boolean testPush() {
        CustomStack<Integer> s = new CustomStack<>();
        for (int i = 0; i < size; i++) {
            s.push(i);
        }
        return true;
    }

    @Benchmark
    public Integer testPop() {
        return stack.pop();
    }

    @Benchmark
    public Integer testPeek() {
        return stack.peek();
    }

    @Benchmark
    public int testSearch() {
        return stack.search(size / 2);
    }

    @Benchmark
    public boolean testEmpty() {
        return stack.empty();
    }

    // --- List Methods (Add / Set / Get) ---

    @Benchmark
    public boolean testAddElement() {
        CustomStack<Integer> s = new CustomStack<>();
        for (int i = 0; i < size; i++) {
            s.add(i);
        }
        return true;
    }

    @Benchmark
    public void testAddAtIndex() {
        stack.add(size / 2, -1);
    }

    @Benchmark
    public boolean testAddAll() {
        CustomStack<Integer> s = new CustomStack<>();
        return s.addAll(sourceCollection);
    }

    @Benchmark
    public boolean testAddAllAtIndex() {
        return stack.addAll(size / 2, subCollection);
    }

    @Benchmark
    public Integer testGet() {
        return stack.get(size / 2);
    }

    @Benchmark
    public Integer testSet() {
        return stack.set(size / 2, -1);
    }

    // --- List Methods (Remove / Clear) ---

    @Benchmark
    public Integer testRemoveIndex() {
        return stack.remove(size / 2);
    }

    @Benchmark
    public boolean testRemoveObject() {
        return stack.remove(Integer.valueOf(size / 2));
    }

    @Benchmark
    public boolean testRemoveAll() {
        return stack.removeAll(removeCollection);
    }

    @Benchmark
    public boolean testRetainAll() {
        return stack.retainAll(removeCollection);
    }

    @Benchmark
    public void testClear() {
        stack.clear();
    }

    // --- Search & Queries ---

    @Benchmark
    public boolean testContains() {
        return stack.contains(size / 2);
    }

    @Benchmark
    public boolean testContainsAll() {
        return stack.containsAll(subCollection);
    }

    @Benchmark
    public int testIndexOf() {
        return stack.indexOf(size / 2);
    }

    @Benchmark
    public int testIndexOfWithFromIndex() {
        return stack.indexOf(size / 2, 0);
    }

    @Benchmark
    public int testLastIndexOfWithFromIndex() {
        return stack.lastIndexOf(size / 2, size - 1);
    }

    @Benchmark
    public int testSize() {
        return stack.size();
    }

    @Benchmark
    public boolean testIsEmpty() {
        return stack.isEmpty();
    }

//    @Benchmark
//    public int testCapacity() {
//        return stack.capacity();
//    }

    // --- Utility / Misc ---

    @Benchmark
    public CustomStack<Integer> testClone() {
        return stack.clone();
    }

//    @Benchmark
//    public void testTrimToSize() {
//        stack.pop();
//        stack.trimToSize();
//    }

//    @Benchmark
//    public void testEnsureCapacity() {
//        stack.ensureCapacity(size * 2);
//    }

    @Benchmark
    public List<Integer> testSubList() {
        return stack.subList(size / 4, (size / 4) * 3);
    }

    @Benchmark
    public void testSort() {
        stack.sort(Comparator.reverseOrder());
    }

    @Benchmark
    public boolean testEquals() {
        return stack.equals(sourceCollection);
    }

    @Benchmark
    public int testHashCode() {
        return stack.hashCode();
    }

    // --- Iterators & Conversions ---

    @Benchmark
    public int testIterator() {
        int sum = 0;
        Iterator<Integer> it = stack.iterator();
        while (it.hasNext()) {
            sum += it.next();
        }
        return sum;
    }

    @Benchmark
    public int testListIterator() {
        int sum = 0;
        ListIterator<Integer> it = stack.listIterator();
        while (it.hasNext()) {
            sum += it.next();
        }
        return sum;
    }

    @Benchmark
    public int testListIteratorWithIndex() {
        int sum = 0;
        ListIterator<Integer> it = stack.listIterator(size / 2);
        while (it.hasNext()) {
            sum += it.next();
        }
        return sum;
    }

    @Benchmark
    public long testSpliterator() {
        Spliterator<Integer> split = stack.spliterator();
        return split.estimateSize();
    }

    @Benchmark
    public Object[] testToArray() {
        return stack.toArray();
    }

    @Benchmark
    public Integer[] testToArrayWithType() {
        return stack.toArray(new Integer[0]);
    }

    @Benchmark
    public String testToString() {
        return stack.toString();
    }

    // --- Main Execution & CSV Writer ---

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CustomStackBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(10)
                .measurementTime(TimeValue.seconds(1))
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .result("custom-stack-v2-results.csv")
                .resultFormat(ResultFormatType.CSV)
                .build();

        Collection<RunResult> results = new Runner(opt).run();
        writeCustomCsv(results);
    }

    private static void writeCustomCsv(Collection<RunResult> results) {
        try (FileWriter writer = new FileWriter("CustomStackV2_jmh_performance.csv")) {
            writer.write("Benchmark;Size;Score (ns/op)\n");
            for (RunResult result : results) {
                String benchmarkName = result.getParams().getBenchmark();
                String shortName = benchmarkName.substring(benchmarkName.lastIndexOf('.') + 1);

                double score = result.getPrimaryResult().getScore();
                String sizeVal = result.getParams().getParam("size");

                writer.write("\"" + shortName + "\";" + (sizeVal != null ? sizeVal : "N/A") + ";" + score + "\n");
            }
            System.out.println("JMH Performance report saved: CustomStack_jmh_performance.csv");
        } catch (IOException e) {
            System.err.println("Failed to write CSV: " + e.getMessage());
        }
    }
}