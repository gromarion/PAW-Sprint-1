package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.common.CommentWrapper;

public class FavouritesPage extends SecuredPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private CommentRepo comments;

	@SuppressWarnings("serial")
	public FavouritesPage() {

		final IModel<List<Comment>> favourites = new LoadableDetachableModel<List<Comment>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Comment> load() {
				return getUserFavouriteComments();
			}
		};
		User user = SocialCthulhuSession.get().getUser();
		IModel<List<CommentWrapper>> comments = new CommentWrapperModel() {

			@Override
			protected List<Comment> transformableLoad() {
				return getUserFavouriteComments();
			}
		};

		add(new CommentsPanel("comments-panel", comments));

		Label noFavourites = new Label("no_favourites",
				getString("no_favourites"));
		add(noFavourites);

		add(new Label("username", user.getUsername()));
		add(new Label("username_suffix", getString("username_suffix")));
		if (favourites != null && !favourites.getObject().isEmpty()) {
			noFavourites.setVisible(false);
		}
	}

	private List<Comment> getUserFavouriteComments() {
		List<Comment> favourites = SocialCthulhuSession.get().getUser()
				.getFavourites();
		List<Comment> favouritesList = new ArrayList<Comment>();
		favouritesList.addAll(favourites);
		return favouritesList;
	}
}
