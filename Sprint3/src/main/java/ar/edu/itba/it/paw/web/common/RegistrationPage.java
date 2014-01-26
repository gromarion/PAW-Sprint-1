package ar.edu.itba.it.paw.web.common;

import java.util.Date;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class RegistrationPage extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserRepo users;

	private transient String name;
	private transient String surname;
	private transient String username;
	private transient String password;
	private transient String confirmPassword;
	private transient String description;
	private transient String secretQuestion;
	private transient String secretAnswer;
	
	private static final int MIN_NAME_LENGTH = 1;
	private static final int MAX_NAME_LENGTH = 32;
	private static final int MIN_SURNAME_LENGTH = 1;
	private static final int MAX_SURNAME_LENGTH = 32;
	private static final int MIN_USERNAME_LENGTH = 1;
	private static final int MAX_USERNAME_LENGTH = 32;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MIN_DESCRIPTION_LENGTH = 1;
	private static final int MAX_DESCRIPTION_LENGTH = 140;
	private static final int MIN_SECRET_QUESTION_LENGTH = 1;
	private static final int MAX_SECRET_QUESTION_LENGTH = 64;
	private static final int MIN_SECRET_ANSWER_LENGTH = 1;
	private static final int MAX_SECRET_ANSWER_LENGTH = 64;
	

	public RegistrationPage() {
		add(new FeedbackPanel("errorPanel"));
		Form<RegistrationPage> form = new Form<RegistrationPage>(
				"registrationForm",
				new CompoundPropertyModel<RegistrationPage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				checkFieldLength();
				if (password.equals(confirmPassword)) {
					User newUser = new User(name, surname, username,
							description, password, null, secretQuestion,
							secretAnswer, new Date(), false);
					users.registerUser(newUser);
					SocialCthulhuSession session = SocialCthulhuSession.get();
					session.signIn(username, password, users);
					if (!continueToOriginalDestination()) {
						setResponsePage(getApplication().getHomePage());
					}
				} else {
					
				}
			}
			
			protected void checkFieldLength() {
				if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
					error(getString("name_length"));
				}
				if (surname.length() < MIN_SURNAME_LENGTH || surname.length() > MAX_SURNAME_LENGTH) {
					error(getString("surname_length"));
				}
				if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH) {
					error(getString("username_length"));
				}
				if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
					error(getString("password_length"));
				}
				if (confirmPassword.length() < MIN_PASSWORD_LENGTH || confirmPassword.length() > MAX_PASSWORD_LENGTH) {
					error(getString("confirmPassword_length"));
				}
				if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
					error(getString("description_length"));
				}
				if (secretQuestion.length() < MIN_SECRET_QUESTION_LENGTH || secretQuestion.length() > MAX_SECRET_QUESTION_LENGTH) {
					error(getString("secretQuestion_length"));
				}
				if (secretAnswer.length() < MIN_SECRET_ANSWER_LENGTH || secretAnswer.length() > MAX_SECRET_ANSWER_LENGTH) {
					error(getString("secretAnswer_length"));
				}
			}
		};
		
		form.add(new TextField<String>("name").setRequired(true));
		form.add(new TextField<String>("surname").setRequired(true));
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new PasswordTextField("confirmPassword"));
		form.add(new TextField<String>("description").setRequired(true));
		form.add(new TextField<String>("secretQuestion").setRequired(true));
		form.add(new TextField<String>("secretAnswer").setRequired(true));
		form.add(new Button("register", new ResourceModel("register")));
		add(form);
	}
}