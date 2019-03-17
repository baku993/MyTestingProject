package hello;

import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.collections4.IteratorUtils;
import hello.User;
import hello.UserRepository;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Iterator;
import java.util.List;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @PostMapping(value="/fun")
    public String addNewUserFromButton (Model model, @ModelAttribute("name") String name
            , @ModelAttribute("email") String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        if (name.equals(null)||email.equals(null))
        {
            User n = new User();
            n.setName(name);
            n.setEmail(email);
            userRepository.save(n);
        }
        return getAllUsersHTML(model);
    }

    @GetMapping(path="/fun")
    public String getAllUsersHTML(Model model) {
        // This returns a JSON or XML with the users
        Iterator<User> allUsers=userRepository.findAll().iterator();
        List<User>  userList= IteratorUtils.toList(allUsers);
        model.addAttribute(userList);
        return "greeting";
    }



}