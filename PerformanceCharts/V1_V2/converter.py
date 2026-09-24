from pathlib import Path
import pandas as pd

HEADER_MAPPING = {
    # Constructors
    "testConstructorDefault": "Constructor()",

    # Operations
    "testAddElement": "add(E)",
    "testAddAtIndex": "add(int, E)",
    "testAddAll": "addAll(Collection)",
    "testAddAllAtIndex": "addAll(int, Collection)",
    "testClear": "clear()",
    "testClone": "clone()",
    "testContains": "contains(Object)",
    "testContainsAll": "containsAll(Collection)",
    "testEmpty": "empty()",
    "testEquals": "equals(Object)",
    "testGet": "get(int)",
    "testHashCode": "hashCode()",
    "testIndexOf": "indexOf(Object)",
    "testIndexOfWithFromIndex": "indexOf(Object, int)",
    "testIsEmpty": "isEmpty()",
    "testIterator": "iterator()",
    "testLastIndexOfWithFromIndex": "lastIndexOf(Object, int)",
    "testListIterator": "listIterator()",
    "testListIteratorWithIndex": "listIterator(int)",
    "testPeek": "peek()",
    "testPop": "pop()",
    "testPush": "push(E)",
    "testRemoveIndex": "remove(int)",
    "testRemoveObject": "remove(Object)",
    "testRemoveAll": "removeAll(Collection)",
    "testRetainAll": "retainAll(Collection)",
    "testSearch": "search(Object)",
    "testSet": "set(int, E)",
    "testSize": "size()",
    "testSort": "sort(Comparator)",
    "testSpliterator": "spliterator()",
    "testSubList": "subList(int, int)",
    "testToArray": "toArray()",
    "testToArrayWithType": "toArray(T[])",
    "testToString": "toString()"
}

COLUMN_ORDER = [
    "Constructor()",
    "add(E)", "add(int, E)", "addAll(Collection)", "addAll(int, Collection)",
    "clear()", "clone()", "contains(Object)", "containsAll(Collection)",
    "empty()", "equals(Object)", "get(int)", "hashCode()",
    "indexOf(Object)", "indexOf(Object, int)", "isEmpty()", "iterator()",
    "lastIndexOf(Object, int)", "listIterator()", "listIterator(int)",
    "peek()", "pop()", "push(E)", "remove(int)", "remove(Object)",
    "removeAll(Collection)", "retainAll(Collection)", "search(Object)",
    "set(int, E)", "size()", "sort(Comparator)", "spliterator()",
    "subList(int, int)", "toArray()", "toArray(T[])", "toString()"
]


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
    convert_to_wide_matrix("CustomStackV2_jmh_performance.csv", "CustomStackV2_wide_matrix.csv")