import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final int TOTAL_BLOCKS = 100; // Assume our disk has 100 blocks
    private static char[] disk = new char[TOTAL_BLOCKS]; // Initially, all blocks are unallocated

    static {
        // Initialize disk blocks to '0' (unallocated)
        for (int i = 0; i < TOTAL_BLOCKS; i++) {
            disk[i] = '0';
        }
    }

    static class File {
        String filename;
        int startBlock;
        int length;

        public File(String filename, int startBlock, int length) {
            this.filename = filename;
            this.startBlock = startBlock;
            this.length = length;
        }
    }

    private static Map<String, File> directory = new HashMap<>(); // Map filename to its File struct

    private static boolean allocateFile(String filename, int size) {
        int freeBlocks = 0;
        int startBlock = -1;

        for (int i = 0; i < TOTAL_BLOCKS; i++) {
            if (disk[i] == '0') {
                if (freeBlocks == 0) {
                    startBlock = i;
                }
                freeBlocks++;

                if (freeBlocks == size) {
                    for (int j = startBlock; j < startBlock + size; j++) {
                        disk[j] = '1';
                    }
                    directory.put(filename, new File(filename, startBlock, size));
                    return true;
                }
            } else {
                freeBlocks = 0;
            }
        }
        return false; // Not enough contiguous blocks found
    }

    private static void displayDisk() {
        for (int i = 0; i < TOTAL_BLOCKS; i++) {
            System.out.print(disk[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        allocateFile("file1", 10);
        allocateFile("file2", 20);
        allocateFile("file3", 5);
        displayDisk();
    }
}
