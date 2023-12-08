package j2ee_project.controller.image;

import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.dao.user.PermissionDAO;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
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
 * This class is a servlet used to get a product image from the Tomcat server or upload it to this server (/images path). It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet(value = "/product/image")
@MultipartConfig
public class ProductImageController extends HttpServlet {
    /**
     * Get a product image from its ID
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String productIdStr = request.getParameter("id");
        int productId = -1;

        if(productIdStr != null && !productIdStr.trim().isEmpty()) {
            try {
                productId = Integer.parseInt(productIdStr);
            } catch(Exception ignore) {}
        }

        if(productId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID must be positive");
            return;
        }

        Product product = ProductDAO.getProduct(productId);

        File root = new File(getServletContext().getRealPath("/")).getParentFile().getParentFile();
        Path imagePath = Paths.get(root.getPath()+"/images/"+product.getImagePath());

        try {
            try (InputStream in = Files.newInputStream(imagePath)) {
                response.setContentType(getServletContext().getMimeType(imagePath.toString()));
                Files.copy(imagePath, response.getOutputStream());
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product's image not found");
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
        Object obj = session.getAttribute("user");
        if(!(obj instanceof Moderator)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } else {
            Moderator moderator = (Moderator) obj;
            if(!moderator.isAllowed(PermissionDAO.getPermission(TypePermission.CAN_MANAGE_PRODUCT))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        Part productIdStrPart = request.getPart("id");
        BufferedReader reader = new BufferedReader(new InputStreamReader(productIdStrPart.getInputStream()));
        String productIdStr = reader.readLine();

        int productId = -1;

        if(productIdStr != null && !productIdStr.trim().isEmpty()) {
            try {
                productId = Integer.parseInt(productIdStr);
            } catch(Exception ignore) {}
        }

        if(productId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID must be positive");
            return;
        }

        Part filePart = request.getPart("file");

        if(!FileService.isImage(filePart)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"An image was expected but another file format has been received");
            return;
        }


        String extension = FileService.getFileExtension(filePart);
        String productName = ProductDAO.getProduct(productId).getName().replaceAll("s/[\\\\/]/g",""); // We remove backslashes and slashes to protect us from path traversal
        extension = extension.replaceAll("[\\\\/]","");
        ProductDAO.setProductImagePath(productId,"products/"+productName+"_"+productId+"."+extension);

        File root = new File(getServletContext().getRealPath("/")).getParentFile().getParentFile();
        Path uploadImagePath = Paths.get(root.getPath()+"/images/"+ProductDAO.getProduct(productId).getImagePath());



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