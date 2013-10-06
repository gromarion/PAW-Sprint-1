package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Hashtag extends AbstractModel {
	private String hashtag;
	
	@OneToOne
	@Column(nullable=false)
	private User author;
	@Column(nullable=false)
	private Date date;

	public Hashtag() {
	}
	
	public Hashtag(String hashtag, User author, Date date) {
		this.hashtag = hashtag;
		this.author = author;
		this.date = date;
	}

	public String getHashtag() {
		return hashtag;
	}

	public User getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
	}
}