package vn.com.tma.postapi.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.tma.postapi.posts.Post;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public List<Comment> getAllByPost(long postId) {
		Post post = new Post();
		post.setPostId(postId);
		return commentRepository.findByPost(post);
	}

	@Override
	public List<Comment> getAll() {
		return commentRepository.findAll();
	}

	@Override
	public Comment get(long id) {
		Optional<Comment> commentOptional = commentRepository.findById(id);
		return commentOptional.orElse(null);
	}

	@Override
	public Comment add(Comment object) {
		return commentRepository.save(object);
	}

	@Override
	public Comment update(long id, Comment object) {
		Optional<Comment> commentOptional = commentRepository.findById(id);
		Comment comment = commentOptional.orElse(null);
		if (comment == null) return null;

		comment.setComment(object.getComment());
		comment.setCreateAt(object.getCreateAt());

		return commentRepository.save(comment);
	}

	@Override
	public void delete(long id) {
		commentRepository.deleteById(id);
	}
}
