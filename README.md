# Simple Image Repository
Image repository app created for the F2021 Shopify Developer Intern Challenge. As this was an open-ended task with no constraints on the technology that can be used, I designed this image repository to be a standalone single-user Java application. Almost all components of this application was coded from scratch, including the GUI (no GUI builder forms were used). The only exception is the thumbnail generator, which relied on an external dependency, thumbnailator. The following screenshot shows the interface of this application.

![](https://github.com/k65yang/SimpleImageRepository/blob/main/App_Interface.png)

## Files Navigation
The following screenshot shows how the file explorer looks in a typical IDE. All code is stored within the src folder. The main folder contains all code related to the data models and the UI. Unit tests are contained within the test folder and were designed using the JUnit framework. The photos are stored in the photos folder and the thumbnails are stored in the thumbnails folder. To launch the application, simply run the AppRunner class.

![](https://github.com/k65yang/SimpleImageRepository/blob/main/App_FolderLayout.PNG)

## App Functionality and Demo
As can be seen from the application interface, this application allows the user to 
- upload an image
- open an image
- delete an image

There is also a scroll pane on the bottom half of the application allowing the user to quickly preview and select an image. 

### Uploading An Image
To upload an image, click the "Upload A New Image Button". It will bring up a file navigator. Simply navigate to the jpeg file to upload and select it. The image will be copied into the photos folder in the project directory and a thumbnail will be automatically generated as well. The scroll pane will then update showing the newly uploaded image.

![](https://github.com/k65yang/SimpleImageRepository/blob/main/App_SelectImage2.PNG)

![](https://github.com/k65yang/SimpleImageRepository/blob/main/App_ImageAdded.png)

### Opening an Image
To see the full size image, click the desired image in the scroll pane and then click the "Open Selected Image" button. This will create a popup showing the full sized image.

![](https://github.com/k65yang/SimpleImageRepository/blob/main/App_ImageOpened.PNG)

### Deleting an Image
To delete an image from the repository, select the image in the scroll pane and then click the "Delete Selected Image" button. This will delete the image from the photos folder and the thumbnails folder. The scroll pane will then update. 

## Future Improvements
As can be noted from the folder layout, in the model package, there is a Tag class, which was unused in this application. Tags can be attached to a Photo as a descriptor and it was my original intention to add a feature which allows users to filter images by the tag. Additionally, Photo objects have a date field and a description field. Features can also be implemented to sort the images by add date and also allow users to add a description to the photo. Unfortunately, due to time constraints, I was not able to add these features in this version of Simple Image Repository.
