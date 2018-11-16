/**
 * This class represents a resource and its information.
 * @author Noah Lenagan, Paris Kelly Skopelitis
 * @version 1.0
 */
public class Resource {

    protected int resourceID;
    protected String title;
    protected int year;
    protected int thumbImage;

    /**
     * Creates a resource with specified id, title, release year and image id.
     * @param resourceID A unique identifier for the resource.
     * @param title The resource title/name.
     * @param year The release year.
     * @param thumbImage The thumbnail image id which identifies a certain image.
     */
    public Resource(int resourceID, String title, int year, int thumbImage) {

        this.resourceID = resourceID;
        this.title = title;
        this.year = year;
        this.thumbImage = thumbImage;

    }

    /**
     * Get the resource id.
     * @return The resource id.
     */
    public int getResourceID() {
        return resourceID;
    }

    /**
     * Get the resource title.
     * @return The resource title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the release year.
     * @return The release year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Get the thumbnail image id.
     * @return The thumbnail image id.
     */
    public int getThumbImage() {
        return thumbImage;
    }

    /**
     * Set the resource id.
     * @param resourceID The new resource id.
     */
    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    /**
     * Set the resource title.
     * @param title The new resource title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the release year.
     * @param year The new release year.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Set the thumbnail image id.
     * @param thumbImage The new thumbnail image id.
     */
    public void setThumbImage(int thumbImage) {
        this.thumbImage = thumbImage;
    }

}
