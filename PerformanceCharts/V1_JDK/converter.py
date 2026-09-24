from pathlib import Path
import pandas as pd

HEADER_MAPPING = {
    # Constructor
    "testConstructorDefault": "Constructor()",

    # Core Stack Methods (LIFO)
    "testPush": "push(E)",
    "testPop": "pop()",
    "testPeek": "peek()",
    "testSearch": "search(Object)",
    "testEmpty": "empty()",

    # List Methods (Add / Set / Get)
    "testAddElement": "add(E)",
    "testAddAtIndex": "add(int, E)",
    "testAddAll": "addAll(Collection)",
    "testAddAllAtIndex": "addAll(int, Collection)",
    "testGet": "get(int)",
    "testSet": "set(int, E)",

    # List Methods (Remove / Clear)
    "testRemoveIndex": "remove(int)",
    "testRemoveObject": "remove(Object)",
    "testRemoveAll": "removeAll(Collection)",
    "testRetainAll": "retainAll(Collection)",
    "testClear": "clear()",

    # Search & Queries
    "testContains": "contains(Object)",
    "testContainsAll": "containsAll(Collection)",
    "testIndexOf": "indexOf(Object)",
    "testIndexOfWithFromIndex": "indexOf(Object, int)",
    "testLastIndexOfWithFromIndex": "lastIndexOf(Object, int)",
    "testSize": "size()",
    "testIsEmpty": "isEmpty()",
    "testCapacity": "capacity()",

    # Utility / Misc
    "testClone": "clone()",
    "testTrimToSize": "trimToSize()",
    "testEnsureCapacity": "ensureCapacity(int)",
    "testSubList": "subList(int, int)",
    "testSort": "sort(Comparator)",
    "testEquals": "equals(Object)",
    "testHashCode": "hashCode()",

    # Iterators & Conversions
    "testIterator": "iterator()",
    "testListIterator": "listIterator()",
    "testListIteratorWithIndex": "listIterator(int)",
    "testSpliterator": "spliterator()",
    "testToArray": "toArray()",
    "testToArrayWithType": "toArray(T[])",
    "testToString": "toString()"
}

COLUMN_ORDER = list(HEADER_MAPPING.values())


def convert_to_wide_matrix(input_file: str | Path, output_file: str | Path) -> None:
    input_path = Path(input_file)
    output_path = Path(output_file)

    if not input_path.exists():
        print(f"Skipping '{input_path}' (file not found).")
        return

    df = pd.read_csv(input_path, sep=';')

    # Extract short method name from fully-qualified package string
    clean_benchmark = df['Benchmark'].astype(str).str.strip('"\t ')
    method_names = clean_benchmark.str.split('.').str[-1]

    # Map method names using HEADER_MAPPING with raw name fallback
    df['Metric'] = method_names.map(HEADER_MAPPING).fillna(method_names)

    # Pivot table and aggregate scores safely using mean
    pivot_df = df.pivot_table(
        index='Size',
        columns='Metric',
        values='Score (ns/op)',
        aggfunc='mean'
    ).round()

    # Safely convert to nullable Int64 to avoid crashes on missing/NaN cells
    pivot_df = pivot_df.astype('Int64')

    # Reorder according to COLUMN_ORDER while appending any unmapped extra columns
    available_columns = [col for col in COLUMN_ORDER if col in pivot_df.columns]
    extra_columns = [col for col in pivot_df.columns if col not in available_columns]
    pivot_df = pivot_df.reindex(columns=available_columns + extra_columns)

    # Export wide matrix CSV
    pivot_df.to_csv(output_path, sep=';')
    print(f"Wide matrix CSV created: {output_path}")


if __name__ == "__main__":
    convert_to_wide_matrix("CustomStack_jmh_performance.csv", "CustomStack_wide_matrix.csv")
    convert_to_wide_matrix("Stack_jmh_performance.csv", "Stack_wide_matrix.csv")