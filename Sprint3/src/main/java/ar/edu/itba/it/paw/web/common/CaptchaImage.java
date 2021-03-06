package ar.edu.itba.it.paw.web.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.web.SocialCthulhuSession;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
@SuppressWarnings({ "serial", "restriction" })
public class CaptchaImage extends NonCachingImage {
	@SpringBean
	private DefaultKaptcha captchaProducer;
	
	public CaptchaImage(String id) {
		super(id);
 
		setImageResource(new DynamicImageResource() {
			public byte[] getImageData(Attributes attributes) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
				try {
					BufferedImage bi = getImageCaptchaService();
					encoder.encode(bi);
					return os.toByteArray();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
 
			private BufferedImage getImageCaptchaService() {
				String capText = captchaProducer.createText();
				SocialCthulhuSession session = SocialCthulhuSession.get();
				session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
				BufferedImage bi = captchaProducer.createImage(capText);
				return bi; 
			}			
		});
	}
}
