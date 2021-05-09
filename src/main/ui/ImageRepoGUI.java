package ui;

import model.Photo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * The GUI class for the application
 */
public class ImageRepoGUI {
    private final JFrame frame;
    private final JPanel panel;
    private JLabel errorLabel;
    private JScrollPane scrollPane;
    private JList menuList;
    private JFileChooser openFileChooser;

    private List<Photo> photoList = new ArrayList<>();

    private final Font headingFont = new Font("SansSerif", Font.BOLD, 24);
    private final Font textFont = new Font("SansSerif", Font.BOLD, 18);
    private final String imagePaths = System.getProperty("user.dir") + System.getProperty("file.separator") + "photos";
    private final String thumbnailPaths = System.getProperty("user.dir") + System.getProperty("file.separator") + "thumbnails";

    /**
     * Constructor of for the application. Creates the JFrame and JPanels and initializes
     * all components
     */
    public ImageRepoGUI() {
        initalizePhotoList();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(560, 500);
        frame.setTitle("Image Repository");
        frame.setResizable(false);


        panel = new JPanel();
        panel.setLayout(null);
        initializePageComponents();

        frame.add(panel);
        frame.validate();
        frame.setVisible(true);
    }

    /**
     * Initializes the photoList by reading in all the current photos in the photos directory
     */
    public void initalizePhotoList() {
        File folderImages = new File(imagePaths);
        File[] imagesList = folderImages.listFiles();

        File folderThumbnails = new File(thumbnailPaths);
        File[] thumbnailsList = folderThumbnails.listFiles();

        for (int i = 0; i < imagesList.length; i++) {
            String nameImage = imagesList[i].getName().replaceFirst("[.][^.]+$", "");
            Photo toAdd = new Photo(nameImage);
            toAdd.loadImage();
            photoList.add(toAdd);
            boolean containsThumbnail = false;
            for (int j = 0; j < thumbnailsList.length; j++) {
                String nameThumbnail = thumbnailsList[j].getName().replaceFirst("[.][^.]+$", "");
                if (nameThumbnail.equals(nameImage)) {
                    containsThumbnail = true;
                    break;
                }
            }
            if (!containsThumbnail) {
                toAdd.createThumbnail();
            } else {
                toAdd.loadThumbnail();
            }
        }
    }

    /**
     * Initializes the page components
     */
    public void initializePageComponents() {
        initializeFileChooser();
        initializeLabels();
        initializeButtons();
        initializeImageScrollPane(false);
    }

    /**
     * Initializes the file chooser object to only search for jpeg files
     */
    public void initializeFileChooser() {
        openFileChooser = new JFileChooser();
        openFileChooser.setFileFilter(new FileNameExtensionFilter("JPG images", "jpg"));

    }

    /**
     * Initializes the JLables used in the app. Basically the heading label and the error label
     */
    public void initializeLabels() {
        JLabel heading = new JLabel("Simple Image Repository");
        heading.setBounds(10, 20, 530, 30);
        heading.setFont(headingFont);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(heading);

        errorLabel = new JLabel("");
        errorLabel.setBounds(10, 170, 530, 20);
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);
    }

    /**
     * Initializes the following buttons: the upload button, the open image button,
     * and the delete image button
     */
    public void initializeButtons() {
        JButton uploadImageButton = new JButton(new AbstractAction("Upload A New Image") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = openFileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        Path source = Paths.get(openFileChooser.getSelectedFile().getAbsolutePath());
                        Path target = Paths.get(imagePaths);
                        Files.copy(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        initializeImageScrollPane(true);
                        errorLabel.setText("");
                    } catch (IOException ex) {
                        errorLabel.setText("Could not open the file");
                    }
                } else {
                    errorLabel.setText("No valid file selected");
                }
            }
        });
        uploadImageButton.setBounds(10, 60, 530, 30);
        uploadImageButton.setFont(textFont);
        uploadImageButton.setFocusPainted(false);
        panel.add(uploadImageButton);

        JButton openImageButton = new JButton(new AbstractAction("Open Selected Image") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Photo photo = getSelectedPhotoObject();
                JFrame popup = new JFrame(photo.getName());
                ImageIcon imageIcon = new ImageIcon(photo.getImage());
                JLabel forImage = new JLabel(imageIcon);

                popup.getContentPane().add(forImage);
                popup.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
                popup.setVisible(true);
            }
        });
        openImageButton.setBounds(10, 100, 530, 30);
        openImageButton.setFont(textFont);
        openImageButton.setFocusPainted(false);
        panel.add(openImageButton);

        JButton deleteImageButton = new JButton(new AbstractAction("Delete Selected Image") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Photo photo = getSelectedPhotoObject();
                File photoToDelete = new File(imagePaths + System.getProperty("file.separator")
                        + photo.getName() + ".jpg");
                File thumbnailToDelete = new File(thumbnailPaths + System.getProperty("file.separator")
                        + photo.getName() +".jpg");
                if (photoToDelete.delete() && thumbnailToDelete.delete()) {
                    photoList.remove(photo);
                }
                initializeImageScrollPane(true);
            }
        });
        deleteImageButton.setBounds(10, 140, 530, 30);
        deleteImageButton.setFont(textFont);
        deleteImageButton.setFocusPainted(false);
        panel.add(deleteImageButton);
    }

    /**
     * Takes the selected entry in the JList and returns the photo object
     *
     * @return the photo object that was selected in the interface or null if none exists
     */
    private Photo getSelectedPhotoObject() {
        Photo selected = null;
        if (menuList.getSelectedValue() != null) {
            JPanel selectedPanel = (JPanel) menuList.getSelectedValue();
            Component[] components = selectedPanel.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof JLabel) {
                    JLabel nameLabel = (JLabel) components[i];
                    String name = nameLabel.getText();
                    for (Photo photo : photoList) {
                        if (photo.getName().equals(name)) {
                            selected = photo;
                        }
                    }
                }
            }
        }
        return selected;
    }

    /**
     * Creates the scroll plane which lists out the images in the repository
     *
     * @param hasInitialized true if calling method to update scroll plane, false otherwise
     */
    public void initializeImageScrollPane(boolean hasInitialized) {
        if (hasInitialized) {
            photoList.clear();
            initalizePhotoList();

            panel.remove(scrollPane);
            createScrollPane();

            panel.repaint();
            panel.add(scrollPane);
            frame.validate();
            frame.setVisible(true);
        } else {
            createScrollPane();
            panel.add(scrollPane);
        }
    }

    /**
     * Helper method to create the scroll plane
     */
    private void createScrollPane() {
        scrollPane = new JScrollPane(getScrollPaneEntries());
        scrollPane.setBounds(10, 190, 530, 275);
        scrollPane.setFont(textFont);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * Helper method to create the JList to be add in the scroll pane from the photos
     * in photoList
     *
     * @return A formatted JList object
     */
    private JList getScrollPaneEntries() {
        menuList = new JList();
        menuList.setCellRenderer(new menuListRenderer());
        Object[] panels = new Object[photoList.size()];
        int index = 0;

        for (Photo photo : photoList) {
            Icon thumbnail = new ImageIcon(photo.getThumbnail());
            JLabel entryLabel = new JLabel(photo.getName(), thumbnail, JLabel.LEFT);
            JPanel entryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            entryPanel.add(entryLabel);
            panels[index] = entryPanel;
            index++;
        }
        menuList.setListData(panels);
        return menuList;
    }

    /**
     * Helper class for rendering each entry in the JList
     */
    private class menuListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            if (value instanceof JPanel) {
                Component component = (Component) value;
                component.setForeground(Color.WHITE);
                component.setBackground(isSelected ? new Color(0f,0f,1f,.5f ) : Color.white);
                return component;
            } else {
                return new JLabel("???");
            }
        }
    }
}
