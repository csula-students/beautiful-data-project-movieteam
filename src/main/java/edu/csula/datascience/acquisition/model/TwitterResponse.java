package edu.csula.datascience.acquisition.model;

public class TwitterResponse {
	private long id;
	private int favCt;
	private int retwtCt;
	private String userName;
	private String text;
	private String date;
	private String source;

	public TwitterResponse(long id, int favCt, int retwtCt, String userName, String text, String date, String source) {
		super();
		this.id = id;
		this.favCt = favCt;
		this.retwtCt = retwtCt;
		this.userName = userName;
		this.text = text;
		this.date = date;
		this.source = source;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the favCt
	 */
	public int getFavCt() {
		return favCt;
	}

	/**
	 * @return the retwtCt
	 */
	public int getRetwtCt() {
		return retwtCt;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwitterResponse other = (TwitterResponse) obj;
		
		if (this.text.equals(other.text)) {			
			return true;
		}		
		return false;
	}

}
