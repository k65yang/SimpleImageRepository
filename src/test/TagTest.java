import model.Photo;
import model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tag class
 */
public class TagTest {

    Tag tag;
    Photo photo;

    @BeforeEach
    void runBefore() {
        tag = new Tag("Dog");
        photo = new Photo("doggo");
    }

    @Test
    void testGetName() {
        assertEquals(tag.getName(), "Dog");
    }

    @Test
    void testAddTagToPhoto() {
        tag.addTagToPhoto(photo);

        Set<Photo> photosWithTag = tag.getPhotos();
        assertEquals(photosWithTag.size(), 1);
        assertTrue(photosWithTag.contains(photo));
    }

    @Test
    void testAddSameTagToPhoto() {
        tag.addTagToPhoto(photo);

        Set<Photo> photosWithTag = tag.getPhotos();
        assertEquals(photosWithTag.size(), 1);
        assertTrue(photosWithTag.contains(photo));

        // adding the tag twice to the same photo
        tag.addTagToPhoto(photo);

        photosWithTag = tag.getPhotos();
        assertEquals(photosWithTag.size(), 1);
        assertTrue(photosWithTag.contains(photo));
    }

    @Test
    void testRemoveTag() {
        tag.addTagToPhoto(photo);
        Set<Photo> photosWithTag = tag.getPhotos();
        assertEquals(photosWithTag.size(), 1);
        assertTrue(photosWithTag.contains(photo));

        tag.removeTagFromPhoto(photo);
        photosWithTag = tag.getPhotos();
        assertEquals(photosWithTag.size(), 0);
    }

    @Test
    void testRemoveNonexistentTag() {
        tag.removeTagFromPhoto(photo);
        Set<Photo> photosWithTag = tag.getPhotos();
        assertEquals(photosWithTag.size(), 0);
    }


}
