package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.ForgotPasswordPage;
import ar.edu.itba.it.paw.web.user.ProfilePage;

public class LoginPage extends BasePage {
	
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserRepo users;
	
	private transient String username;
	private transient String password;

	public LoginPage() {
		if(((SocialCthulhuSession)getSession()).isSignedIn()) {
			SocialCthulhuSession session = SocialCthulhuSession.get();
			setResponsePage(new ProfilePage(users.getUser(session.getUsername()).getId()));
		}
		add(new FeedbackPanel("feedback"));
		Form<LoginPage> form = new Form<LoginPage>("loginForm", new CompoundPropertyModel<LoginPage>(this)) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				SocialCthulhuSession session = SocialCthulhuSession.get();

				if (session.signIn(username, password, users)) {
					continueToOriginalDestination();
					setResponsePage(new ProfilePage(users.getUser(session.getUsername()).getId()));
				} else {
					error(getString("invalidCredentials"));
				}
			}
		};

		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("login", new ResourceModel("login")));
		add(form);
		add(new BookmarkablePageLink<Void>("forgotPassword", ForgotPasswordPage.class));
		add(new BookmarkablePageLink<Void>("registration", RegistrationPage.class));
		add(new Top10HashtagsPanel("top10hashtags"));
	}
}
