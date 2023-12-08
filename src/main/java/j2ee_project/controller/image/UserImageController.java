package j2ee_project.controller.image;

import j2ee_project.dao.user.UserDAO;
import j2ee_project.model.user.User;
import j2ee_project.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is a servlet used to get a user image from the Tomcat server or upload it to this server (/images path). It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet(value = "/user/image")
@MultipartConfig
public class UserImageController extends HttpServlet {
    /**
     * Get a user image from its ID
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String userIdStr = request.getParameter("id");
        int userId = -1;

        if(userIdStr != null && !userIdStr.trim().isEmpty()) {
            try {
                userId = Integer.parseInt(userIdStr);
            } catch(Exception ignore) {}
        }

        if(userId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID must be positive");
            return;
        }

        User user = UserDAO.getUser(userId);

        if(user == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found for this ID");
            return;
        }

        File root = new File(getServletContext().getRealPath("/")).getParentFile().getParentFile();
        Path imagePath = Paths.get(root.getPath()+"/images/users/u"+userId);

        try {
            try (InputStream in = Files.newInputStream(imagePath)) {
                response.setContentType(getServletContext().getMimeType(imagePath.toString()));
                Files.copy(imagePath, response.getOutputStream());
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User's image not found");
        }
    }

    /**
     * Save the given image in the Tomcat server
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Part productIdStrPart = request.getPart("id");
        BufferedReader reader = new BufferedReader(new InputStreamReader(productIdStrPart.getInputStream()));
        String userIdStr = reader.readLine();
        int userId = -1;

        if(userIdStr != null && !userIdStr.trim().isEmpty()) {
            try {
                userId = Integer.parseInt(userIdStr);
            } catch(Exception ignore) {}
        }

        if(userId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID must be positive");
            return;
        }

        Object obj = session.getAttribute("user");
        User user = null;
        if(obj instanceof User) {
            user = (User) obj;
        }

        if(user.getId() != userId) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You can't edit the image of the user associated to this ID. It's not your account");
        }

        Part filePart = request.getPart("file");

        if(!FileService.isImage(filePart)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"An image was expected but another file format has been received");
            return;
        }


        String extension = FileService.getFileExtension(filePart);
        extension = extension.replaceAll("[\\\\/]","");

        File root = new File(getServletContext().getRealPath("/")).getParentFile().getParentFile();
        Path uploadImagePath = Paths.get(root.getPath()+"/images/users/u"+userId+"."+extension);

        OutputStream out = null;
        InputStream fileContent = null;

        try {
            out = new FileOutputStream(new File(uploadImagePath.toString()));
            fileContent = filePart.getInputStream();

            int nbBytesRead = 0;
            final byte[] bytes = new byte[1024 * 128]; // buffer size = 128 KB

            while ((nbBytesRead = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, nbBytesRead);
            }

            response.sendError(HttpServletResponse.SC_OK);
        } catch (FileNotFoundException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"You did not specify a file to upload or the file could not be uploaded");
            System.err.println(e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }
    }
}