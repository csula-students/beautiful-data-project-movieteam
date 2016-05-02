package edu.csula.datascience.acquisition.twitter;

/**
 * Mock raw twitter data
 */
public class MockTwitterData {
	private long id;
	private int favCt;
	private int retwtCt;
	private String userName;
	private String text;
	private String date;
	private String source;

	public MockTwitterData(long id, int favCt, int retwtCt, String userName, String text, String date, String source) {
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
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the favCt
	 */
	public int getFavCt() {
		return favCt;
	}

	/**
	 * @param favCt
	 *            the favCt to set
	 */
	public void setFavCt(int favCt) {
		this.favCt = favCt;
	}

	/**
	 * @return the retwtCt
	 */
	public int getRetwtCt() {
		return retwtCt;
	}

	/**
	 * @param retwtCt
	 *            the retwtCt to set
	 */
	public void setRetwtCt(int retwtCt) {
		this.retwtCt = retwtCt;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
}
