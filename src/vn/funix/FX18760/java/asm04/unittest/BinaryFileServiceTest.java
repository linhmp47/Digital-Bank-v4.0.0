package vn.funix.FX18760.java.asm04.unittest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.funix.FX18760.java.asm04.fileService.BinaryFileService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileServiceTest {
    private static final String TEST_FILE_NAME = "test.bin";
    @BeforeEach
    void setUp() {
        // Chuẩn bị môi trường trước mỗi test
        List<String> testData = new ArrayList<>();
        testData.add("Test data 1");
        testData.add("Test data 2");
        BinaryFileService.writeFile(TEST_FILE_NAME, testData);
    }

    @AfterEach
    void tearDown() {
        // Dọn dẹp môi trường sau mỗi test
        File file = new File(TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testReadFile() {
        List<String> testData = BinaryFileService.readFile(TEST_FILE_NAME);
        assertNotNull(testData);
        assertEquals(2, testData.size());
        assertEquals("Test data 1", testData.get(0));
        assertEquals("Test data 2", testData.get(1));
    }

    @Test
    void testWriteFile() {
        List<String> testData = new ArrayList<>();
        testData.add("New test data 1");
        testData.add("New test data 2");

        BinaryFileService.writeFile(TEST_FILE_NAME, testData);

        List<String> readData = BinaryFileService.readFile(TEST_FILE_NAME);
        assertNotNull(readData);
        assertEquals(2, readData.size());
        assertEquals("New test data 1", readData.get(0));
        assertEquals("New test data 2", readData.get(1));
    }
}
