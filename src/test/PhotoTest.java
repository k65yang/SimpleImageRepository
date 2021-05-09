import model.Photo;
import model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Photo class
 */
public class PhotoTest {

    Photo doggo;
    Date addedDate;

    String userDir = System.getProperty("user.dir");
    String fileSep = System.getProperty("file.separator");

    @BeforeEach
    void runBefore() {
        doggo = new Photo("doggo");
        addedDate = new Date();
    }

    @Test
    void testGetName() {
        assertEquals(doggo.getName(), "doggo");
    }

    @Test
    void testSetAndGetDescription() {
        assertNull(doggo.getDescription());

        doggo.setDescription("The best doggo");
        assertEquals(doggo.getDescription(), "The best doggo");
    }

    @Test
    void testDateAdded() {
        assertEquals(doggo.getDateAdded(), addedDate);
    }

    @Test
    void testLoadExistingImageAndThumbnail() {
        doggo.loadImage();
        doggo.loadThumbnail();

        // check that a thumbnail has been generated
        File thumbnail = new File(userDir + fileSep + "thumbnails" + fileSep + doggo.getName() + ".jpg");
        assertTrue(thumbnail.exists());

        Photo temp = new Photo("good boi");
        temp.loadImage();
        temp.loadThumbnail();
    }

    @Test
    void testLoadNonExistentImageAndThumbnail() {
        Photo asdf = new Photo("asdf");
        asdf.loadImage();
        asdf.loadThumbnail();

        // check no thumbnail has been generated
        File thumbnail = new File(userDir + fileSep + "thumbnails" + fileSep + asdf.getName() + ".jpg");
        assertFalse(thumbnail.exists());
    }

    @Test
    void testAddOneTag() {
        Tag tag = new Tag("Dog");
        doggo.addTag(tag);

        Set<Tag> tags = doggo.getTags();
        assertEquals(tags.size(), 1);
        assertTrue(tags.contains(tag));
    }

    @Test
    void testAddSameTagTwice() {
        Tag tag = new Tag("Dog");
        doggo.addTag(tag);
        doggo.addTag(tag);

        Set<Tag> tags = doggo.getTags();
        assertEquals(tags.size(), 1);
        assertTrue(tags.contains(tag));
    }

    @Test
    void testAddSimilarTagTwice() {
        Tag tag = new Tag("Dog");
        doggo.addTag(tag);

        Tag tag1 = new Tag("Dog");
        doggo.addTag(tag1);

        Set<Tag> tags = doggo.getTags();
        assertEquals(tags.size(), 1);
        assertTrue(tags.contains(tag));
    }

    @Test
    void testRemoveTag() {
        Tag tag = new Tag("Dog");
        doggo.addTag(tag);

        Set<Tag> tags = doggo.getTags();
        assertEquals(tags.size(), 1);
        assertTrue(tags.contains(tag));

        doggo.removeTag(tag);
        assertEquals(tags.size(), 0);
    }

    @Test
    void testRemoveNonexistentTag() {
        Set<Tag> tags = doggo.getTags();
        assertEquals(tags.size(), 0);

        doggo.removeTag(new Tag("Animal"));
        assertEquals(tags.size(), 0);
    }
}
