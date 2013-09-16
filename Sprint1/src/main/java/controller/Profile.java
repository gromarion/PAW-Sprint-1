package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Comment;
import model.User;
import services.CommentService;
import services.UserService;

@SuppressWarnings("serial")
public class Profile extends AbstractController{

	private CommentService commentService = CommentService.getInstance();
	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("user");
		User user = userService.getUsuer(username);
		req.setAttribute("user", user);
		List<Comment> comments = commentService.getComments(user);
		for (Comment comment : comments) {
			comment.setComment(getProcessedComment(comment.getComment()));
		}
		req.setAttribute("comments", comments);
		req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String aux = req.getParameter("comment");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (aux.length() > 0) {
			Comment comment = new Comment(user, new Date(), aux,
					commentService.getHashtagList(aux, user));
			commentService.save(comment);
		}
		resp.sendRedirect("profile?user=" + user.getUsername());
	}
}
