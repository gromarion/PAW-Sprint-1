package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;
import network.CommentDAO;
import network.HashtagDAO;

public class CommentService {

	private CommentDAO commentDao;
	private HashtagDAO hashtagDao;

	private static CommentService instance;

	public static CommentService getInstance() {
		if (instance == null) {
			instance = new CommentService();
		}
		return instance;
	}

	private CommentService() {
		commentDao = CommentDAO.getInstance();
		hashtagDao = HashtagDAO.getInstance();
	}

	public List<Hashtag> getHashtagList(String comment, User author) {
		String[] aux = comment.split("#[A-Za-z0-9]");
		List<Hashtag> ans = new ArrayList<Hashtag>();
		for (String string : aux) {
			Hashtag tag = hashtagDao.getHashTag(string);
			if (tag == null) {
				tag = new Hashtag(string, author, new Date());
				hashtagDao.save(tag);
			}
			ans.add(tag);
		}
		return ans;
	}

	public void save(Comment comment) {
		commentDao.save(comment);
	}

	public List<Comment> getComments(User user) {
		List<Comment> comments = commentDao.getComments(user);
		sortComments(comments);
		return comments;
	}

	private void sortComments(List<Comment> comments) {
		Collections.sort(comments, new Comparator<Comment>() {
			public int compare(Comment o1, Comment o2) {
				return -1*o1.getDate().compareTo(o2.getDate());
			}
		});
	}
}
