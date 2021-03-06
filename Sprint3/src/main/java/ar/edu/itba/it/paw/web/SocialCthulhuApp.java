package ar.edu.itba.it.paw.web;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.web.common.CookieService;
import ar.edu.itba.it.paw.web.common.HibernateRequestCycleListener;
import ar.edu.itba.it.paw.web.common.LoginPage;
import ar.edu.itba.it.paw.web.hashtag.HashtagDetailPage;
import ar.edu.itba.it.paw.web.user.ProfilePage;

@Component
public class SocialCthulhuApp extends WebApplication {

	private final SessionFactory sessionFactory;
	public static final ResourceReference SEPARTOR = new PackageResourceReference(SocialCthulhuApp.class, "resources/topbar_separator.png");
	public static final ResourceReference HOME = new PackageResourceReference(SocialCthulhuApp.class, "resources/home.png");
	public static final ResourceReference TOP_10_HASHTAGS = new PackageResourceReference(SocialCthulhuApp.class, "resources/t10h.png");
	public static final ResourceReference TITLE = new PackageResourceReference(SocialCthulhuApp.class, "resources/title.png");
	public static final ResourceReference SUGGESTED_USERS = new PackageResourceReference(SocialCthulhuApp.class, "resources/suggest_friends.png");
	public static final ResourceReference FAVOURITES = new PackageResourceReference(SocialCthulhuApp.class, "resources/Favourites.png");
	public static final ResourceReference DEFAULT_IMAGE = new PackageResourceReference(SocialCthulhuApp.class, "resources/default_picture.png");
	private CookieService cookieService = new CookieService();
	private SessionProvider sessionProvider;
	public static final ResourceReference POPULAR_ICON = new PackageResourceReference(SocialCthulhuApp.class, "resources/popular.png");
	
	@Autowired
	public SocialCthulhuApp(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return LoginPage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		mountPage("/profile/${username}", ProfilePage.class);
		mountPage("/hashtag/${hashtag}", HashtagDetailPage.class);
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
		sessionProvider = new SessionProvider(cookieService);
	}

	@Override
	public Session newSession(Request request, Response response) {
		return sessionProvider.createNewSession(request);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		return converterLocator;
	}
	
	public CookieService getCookieService() {
		return cookieService;
	}
}
