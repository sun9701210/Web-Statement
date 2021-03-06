package webstmt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class CKFinderIndexController {
    @RequestMapping("/dashboard/ckfinder")
    public void index(HttpServletResponse response) {
        // Redirect to CKFinder's browser page.
        response.setHeader("Location", "/ckfinder/static/ckfinder.html");
        response.setStatus(302);
    }
}