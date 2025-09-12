import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HashingProject {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose Hashing Type:");
        System.out.println("1. Linear Probing");
        System.out.println("2. Quadratic Probing");
        System.out.println("3. Double Hashing");
        System.out.println("4. Separate Chaining");
        System.out.println("----------------------------------------");
        System.out.print("Enter Your Choice : ");
        int choice = scanner.nextInt();

        String typeName = switch (choice) {
            case 1 -> "linear";
            case 2 -> "quadratic";
            case 3 -> "double";
            case 4 -> "chaining";
            default -> null;
        };

        if (typeName == null) {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Enter hash table size: ");
        int tableSize = scanner.nextInt();

        System.out.print("Enter number of elements: ");
        int count = scanner.nextInt();

        int[] values = new int[count];

        for (int i = 0; i < count; i++) {
            try {
                // Check if the number of elements is greater than the size of the array
                if (count > values.length) {
                    throw new IndexOutOfBoundsException("The number of elements is greater than the array size.");
                }

                System.out.print("Element " + (i + 1) + ": ");
                values[i] = scanner.nextInt();
                saveToFile(typeName + ".txt", values);

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: The number of elements is greater than the array size.");
                System.out.println("Setting default values for the array.");

                // On error, you can either set default values for the array or take another action
                Arrays.fill(values, 0); // Reset all elements to 0 (or another default value)

                break; // Exit the loop if the array size has been exceeded
            }
        }

        switch (choice) {
            case 1 -> {
                LinearHash linear = new LinearHash(tableSize);
                for (int v : values) linear.insert(v);
                linear.display();
            }
            case 2 -> {
                QuadraticHash quadratic = new QuadraticHash(tableSize);
                for (int v : values) quadratic.insert(v);
                quadratic.display();
            }
            case 3 -> {
                DoubleHash doubleHash = new DoubleHash(tableSize);
                for (int v : values) doubleHash.insert(v);
                doubleHash.display();
            }
            case 4 -> {
                SeparateChain separate = new SeparateChain(tableSize);
                for (int v : values) separate.insert(v);
                separate.display();
            }
        }

        scanner.close();
    }

    public static void saveToFile(String filename, int[] values) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int val : values) {
                writer.write(val + "\n");
            }
            System.out.println("Data saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
}

// Linear Hashing Class
class LinearHash {
    Integer[] table;
    int size;

    public LinearHash(int size) {
        this.size = size;
        table = new Integer[size];
    }

    private int hash(int key) {
        return key % size;
    }

    public void insert(int key) {
        int index = hash(key);
        while (table[index] != null) {
            index = (index + 1) % size;
        }
        table[index] = key;
    }

    public void display() {
        System.out.println("Linear Probing Table:");
        for (int i = 0; i < size; i++) {
            System.out.println(i + ": " + (table[i] != null ? table[i] : "empty"));
        }
    }
}

// Quadratic Hashing Class
class QuadraticHash {
    Integer[] table;
    int size;

    public QuadraticHash(int size) {
        this.size = size;
        table = new Integer[size];
    }

    private int hash(int key) {
        return key % size;
    }

    public void insert(int key) {
        int index = hash(key);
        int i = 0;
        while (table[(index + i * i) % size] != null) {
            i++;
        }
        table[(index + i * i) % size] = key;
    }

    public void display() {
        System.out.println("Quadratic Probing Table:");
        for (int i = 0; i < size; i++) {
            System.out.println(i + ": " + (table[i] != null ? table[i] : "empty"));
        }
    }
}

// Double Hashing Class (Modified for Integers)
class DoubleHash {
    Integer[] table;
    int size;

    public DoubleHash(int capacity) {
        table = new Integer[capacity];
        size = capacity;
    }

    private int hash1(int key) {
        return key % size;
    }

    private int hash2(int key) {
        int prime = 12;
        return (key % prime);
    }

    public void insert(int key) {
        int index = hash1(key);
        int stepSize = hash2(key);

        int tries = 0;
        while (table[index] != null && tries < size) {
            index = (index + stepSize) % size;
            tries++;
        }

        if (tries < size) {
            table[index] = key;
        }
        else if (stepSize == 0){
            System.out.println("Failed Infinty Loop With Key :" +key);
        }
        else {
            System.out.println("Failed to insert \"" + key + "\" — Table is full or collision cycle occurred.");
        }
    }

    public void display() {
        System.out.println("Double Hashing Hash Table:");
        for (int i = 0; i < size; i++) {
            System.out.println(i + ": " + (table[i] != null  ? table[i] : "empty"));
        }
    }
}

// Separate Chaining Class
class SeparateChain {
    LinkedList<Integer>[] table;
    int size;

    public SeparateChain(int size) {
        this.size = size;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(int key) {
        return key % size;
    }

    public void insert(int key) {
        int index = hash(key);
        table[index].add(key);
    }

    public void display() {
        System.out.println("Separate Chaining Table:");
        for (int i = 0; i < size; i++) {
            System.out.print(i + ": ");
            for (int val : table[i]) {
                System.out.print(val + " -> ");
            }
            System.out.println("null");
        }
    }
}