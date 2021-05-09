package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A tag object. Tags have a name and a set of photos which they are associated with
 */
public class Tag {
    private final String name;
    private Set<Photo> photos = new HashSet<>();

    /**
     * Constructor for a Tag object
     *
     * @param name The name of the tag
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * @return The name of the tag
     */
    public String getName() {
        return name;
    }

    /**
     * @return The set of photos that have this tag
     */
    public Set<Photo> getPhotos() {
        return Collections.unmodifiableSet(photos);
    }

    /**
     * Adds this tag to a photo
     *
     * @param photo
     */
    public void addTagToPhoto(Photo photo) {
        if (!photos.contains(photo)) {
            photos.add(photo);
            photo.addTag(this);
        }
    }

    /**
     * Removes this tag from a photo
     *
     * @param photo
     */
    public void removeTagFromPhoto(Photo photo) {
        if (photos.contains(photo)) {
            photos.remove(photo);
            photo.removeTag(this);
        }
    }

    /**
     * Overriding equals such that tags with the same name will be considered the same
     *
     * @param o
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
