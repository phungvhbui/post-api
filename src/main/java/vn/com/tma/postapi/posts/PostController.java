package vn.com.tma.postapi.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.tma.postapi.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	private final PostServiceImpl postService;

	@Autowired
	public PostController(PostServiceImpl postService) {
		this.postService = postService;
	}

	@GetMapping()
	public ResponseEntity<List<Post>> getAllPost() {
		return ResponseEntity.ok(postService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable(value = "id") Long postId) throws ResourceNotFoundException {
		Post post = postService.get(postId);
		if (post == null) {
			throw new ResourceNotFoundException("Post not found on :: " + postId);
		}
		return ResponseEntity.ok(post);
	}

	@PostMapping
	public ResponseEntity<Post> addPost(@RequestBody Post post) {
		Post _post = postService.add(post);
		return new ResponseEntity<>(_post, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable(value = "id") Long postId, @RequestBody Post post) throws ResourceNotFoundException {
		Post _post = postService.update(postId, post);
		if (_post == null) {
			throw new ResourceNotFoundException("Post not found on :: " + postId);
		}
		return ResponseEntity.ok(_post);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Post> deletePost(@PathVariable(value = "id") Long postId) throws ResourceNotFoundException {
		try {
			postService.delete(postId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Post not found on :: " + postId);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
