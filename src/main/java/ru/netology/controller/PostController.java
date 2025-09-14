package ru.netology.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
  private final PostService service;
  private final ObjectMapper mapper = new ObjectMapper();

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse resp) throws IOException {
    List<Post> posts = service.all();
    resp.setContentType("application/json");
    resp.getWriter().write(mapper.writeValueAsString(posts));
  }

  public void getById(long id, HttpServletResponse resp) throws IOException {
    Optional<Post> post = service.getById(id);
    resp.setContentType("application/json");
    if (post.isPresent()) {
      resp.getWriter().write(mapper.writeValueAsString(post.get()));
    } else {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      resp.getWriter().write("{\"error\":\"Not Found\"}");
    }
  }

  public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Post post = mapper.readValue(req.getReader(), Post.class);
    Post saved = service.save(post);
    resp.setContentType("application/json");
    resp.getWriter().write(mapper.writeValueAsString(saved));
  }

  public void removeById(long id, HttpServletResponse resp) {
    service.removeById(id);
    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }
}

