package vn.com.tma.blogapi.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.tma.blogapi.common.CommonService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements CommonService<Post> {
	private final PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
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
		return postRepository.save(object);
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
