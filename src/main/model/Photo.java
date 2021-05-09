package model;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A class representing a photo object. Each photo will have a name, date added.
 * A photo can optionally have a set of tags and also a description.
 */

public class Photo {
    // variables for locating where an image is stored
    private static final String IMAGE_DIR = "photos";
    private static final String THUMBNAILS_DIR = "thumbnails";
    private static final String IMAGE_TYPE = ".jpg";
    private static final String PROJECT_DIRECTORY_PATH = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private final String imageFilePath;
    private final String thumbnailFilePath;

    private final String name;
    private String description;
    private final Date dateAdded;
    private Set<Tag> tags = new HashSet<>();

    private BufferedImage image;
    private BufferedImage thumbnail;

    /**
     * Constructor for a photo object
     *
     * @pre An jpeg image file of the same name must already exist in the image folder
     * @param name The name of the existing image file
     */
    public Photo(String name) {
        this.name = name;
        dateAdded = new Date();
        imageFilePath = PROJECT_DIRECTORY_PATH + FILE_SEPARATOR + IMAGE_DIR + FILE_SEPARATOR + this.name + IMAGE_TYPE;
        thumbnailFilePath = PROJECT_DIRECTORY_PATH + FILE_SEPARATOR + THUMBNAILS_DIR + FILE_SEPARATOR + this.name + IMAGE_TYPE;
    }

    /**
     * @return returns the name of the photo
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the description of the photo
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this photo
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return returns the date added of this photo
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * @return the image associated with this photo object
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return the thumbnail image associated with this photo object
     */
    public Image getThumbnail() {
        return thumbnail;
    }

    /**
     * Adds a tag to this photo
     *
     * @param tag A tag to be added to this photo
     */
    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            tag.addTagToPhoto(this);
        }
    }

    /**
     * Removes a tag to this photo
     *
     * @param tag A tag to be removed from this photo
     */
    public void removeTag(Tag tag) {
        if (tags.contains(tag)) {
            tags.remove(tag);
            tag.removeTagFromPhoto(this);
        }
    }

    /**
     * @return The set of tags associated with this photo
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Loads the image from file
     */
    public void loadImage() {
        try {
            image = ImageIO.read(new File(imageFilePath));
        } catch (IOException e) {
            // silent return as nothing would happen
        }
    }

    /**
     * Creates a thumbnail from the photo (scales the picture into a smaller size)
     */
    public void createThumbnail() {
        try {
            Thumbnails.of(new File(imageFilePath)).size(64, 64).toFile(thumbnailFilePath);
            thumbnail = ImageIO.read(new File(thumbnailFilePath));
        } catch (IOException e) {
            // silent return as nothing would happen
        }
    }

    /**
     * Loads the thumbnail from file
     */
    public void loadThumbnail() {
        try {
            thumbnail = ImageIO.read(new File(thumbnailFilePath));
        } catch (IOException e) {
            // silent return as nothing would happen
        }
    }
}
