package vn.com.tma.postapi.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.tma.postapi.comment.Comment;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;

	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public List<Post> getAll() {
		return postRepository.findAll();
	}

	@Override
	public Post get(long id) {
		Optional<Post> postOptional = postRepository.findById(id);
		return postOptional.orElse(null);
	}

	@Override
	public Post add(Post object) {
		Post post = new Post();
		post.setTitle(object.getTitle());
		post.setContent(object.getContent());
		post.setCreatedAt(object.getCreatedAt());
		List<Comment> comments = new ArrayList<>();
		object.getComments().forEach(comment -> {
			comment.setPost(post);
			comments.add(comment);
		});
		post.setComments(comments);
		return postRepository.save(post);
	}

	@Override
	public Post update(long id, Post object) {
		Optional<Post> postOptional = postRepository.findById(id);
		Post post = postOptional.orElse(null);
		if (post == null) return null;

        post.setTitle(object.getTitle());
        post.setContent(object.getContent());
        post.setCreatedAt(object.getCreatedAt());

        return postRepository.save(post);
    }

	@Override
	public void delete(long id) {
		postRepository.deleteById(id);
	}
}
