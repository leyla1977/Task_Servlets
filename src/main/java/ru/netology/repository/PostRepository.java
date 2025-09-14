package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


// Репозиторий
@Repository
public class PostRepository {
  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  public Post save(Post post) {
    if (post.getId() == 0 || !posts.containsKey(post.getId())) {
      long newId = idCounter.incrementAndGet();
      post.setId(newId);
    }
    posts.put(post.getId(), post);
    return post;
  }

  public Optional<Post> findById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public List<Post> findAll() {
    return new ArrayList<>(posts.values());
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}

