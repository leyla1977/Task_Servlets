package ru.netology.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.AppConfig;
import ru.netology.controller.PostController;

import java.io.IOException;

@WebServlet("/api/posts/*")
public class MainServlet extends HttpServlet {

  private PostController controller;

  @Override
  public void init() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      final var path = req.getPathInfo();
      final var method = req.getMethod();

      if ("GET".equals(method) && (path == null || path.equals("/"))) {
        controller.all(resp);
        return;
      }

      if ("GET".equals(method) && path.matches("/\\d+")) {
        long id = Long.parseLong(path.substring(1));
        controller.getById(id, resp);
        return;
      }

      if ("POST".equals(method) && (path == null || path.equals("/"))) {
        controller.save(req, resp);
        return;
      }

      if ("DELETE".equals(method) && path.matches("/\\d+")) {
        long id = Long.parseLong(path.substring(1));
        controller.removeById(id, resp);
        return;
      }

      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

