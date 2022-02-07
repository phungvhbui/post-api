package vn.com.tma.postapi.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.tma.postapi.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	private final CommentServiceImpl commentService;

	@Autowired
	public CommentController(CommentServiceImpl commentService) {
		this.commentService = commentService;
	}

	@GetMapping()
	public ResponseEntity<List<Comment>> getAllComments() {
		return ResponseEntity.ok(commentService.getAll());
	}

	@GetMapping("/posts")
	public ResponseEntity<List<Comment>> getCommentsByPost(@RequestParam(value = "postId") Long postId) {
		return ResponseEntity.ok(commentService.getAllByPost(postId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") Long commentId) throws ResourceNotFoundException {
		Comment comment = commentService.get(commentId);
		if (comment == null) {
			throw new ResourceNotFoundException("Comment not found on :: " + commentId);
		}
		return ResponseEntity.ok(comment);
	}

	@PostMapping
	public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
		Comment _comment = commentService.add(comment);
		return new ResponseEntity<>(_comment, HttpStatus.CREATED);
	}

	@PostMapping("/posts")
	public ResponseEntity<Comment> addCommentToPost(@RequestParam(value = "postId") Long postId, @RequestBody Comment comment) throws ResourceNotFoundException {
		Comment _comment;
		try {
			_comment = commentService.addToPost(postId, comment);
		} catch (DataIntegrityViolationException e) {
			throw new ResourceNotFoundException("Post not found on :: " + postId);
		}
		return new ResponseEntity<>(_comment, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") Long commentId, @RequestBody Comment comment) throws ResourceNotFoundException {
		Comment _comment = commentService.update(commentId, comment);
		if (_comment == null) {
			throw new ResourceNotFoundException("Comment not found on :: " + commentId);
		}
		return ResponseEntity.ok(_comment);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Comment> deleteComment(@PathVariable(value = "id") Long commentId) throws ResourceNotFoundException {
		try {
			commentService.delete(commentId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Comment not found on :: " + commentId);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
