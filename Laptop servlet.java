import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// LaptopServlet class extending HttpServlet
public class LaptopServlet extends HttpServlet {
    private LaptopManager laptopManager; // Assuming you have a LaptopManager class for managing laptop data

    // Initialize the servlet
    public void init() {
        laptopManager = new LaptopManager();
    }

    // Handle GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            // If no action specified, show list of laptops
            List<Laptop> laptops = laptopManager.getAllLaptops();
            request.setAttribute("laptops", laptops);
            RequestDispatcher dispatcher = request.getRequestDispatcher("laptop-list.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("add")) {
            // Show form for adding a new laptop
            RequestDispatcher dispatcher = request.getRequestDispatcher("add-laptop.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("edit")) {
            // Show form for editing a laptop
            String laptopId = request.getParameter("id");
            Laptop laptop = laptopManager.getLaptopById(laptopId);
            request.setAttribute("laptop", laptop);
            RequestDispatcher dispatcher = request.getRequestDispatcher("edit-laptop.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("delete")) {
            // Delete a laptop
            String laptopId = request.getParameter("id");
            laptopManager.deleteLaptop(laptopId);
            response.sendRedirect("laptops"); // Redirect to the list of laptops
        }
    }

    // Handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("add")) {
            // Add a new laptop
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            // Validate and process the input, then add the laptop
            laptopManager.addLaptop(new Laptop(brand, model));
            response.sendRedirect("laptops"); // Redirect to the list of laptops
        } else if (action.equals("edit")) {
            // Edit an existing laptop
            String laptopId = request.getParameter("id");
            String brand = request.getParameter("brand");
            String model = request.getParameter("model");
            // Validate and process the input, then update the laptop
            laptopManager.updateLaptop(laptopId, brand, model);
            response.sendRedirect("laptops"); // Redirect to the list of laptops
        }
    }
}
