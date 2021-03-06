package ar.edu.itba.it.paw.web.common;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.UsersPanel;

public class SearchPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private String search;
	@SpringBean
	private UserRepo users;

	@SuppressWarnings("serial")
	public SearchPage(String searchText) {
		super();
		search = searchText != null ? searchText : "";
		add(new Label("search", getString("searchTitle") + "\"" + search + "\""));
		
		IModel<List<User>> userModel = new LoadableDetachableModel<List<User>>() {

			@Override
			protected List<User> load() {
				List<User> userList = search == "" ? users.getAll() : users.getUsersWithName(search);
				return userList;
			}
		};
		add(new UsersPanel("users-panel", userModel));
	}
}
