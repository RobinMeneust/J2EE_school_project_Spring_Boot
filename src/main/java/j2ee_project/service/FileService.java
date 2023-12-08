package j2ee_project.service;

import jakarta.servlet.http.Part;

/**
 * File manager
 */
public class FileService {
    /**
     * Gets file extension from a Part object
     *
     * @param part the part
     * @return the file extension
     */
    public static String getFileExtension(Part part) {
        String fileName = null;
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                break;
            }
        }

        if(fileName == null) {
            return "";
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    /**
     * Check if the given Part is an image
     *
     * @param part the part
     * @return True if it's an image and false otherwise
     */
    public static boolean isImage(Part part) {
        return part.getContentType().startsWith("image/");
    }
}
