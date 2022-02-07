package vn.com.tma.postapi.comment;

import vn.com.tma.postapi.common.CommonService;

import java.util.List;

public interface CommentService extends CommonService<Comment> {
	List<Comment> getAllByPost(long postId);
	Comment addToPost(long postId, Comment comment);
}
