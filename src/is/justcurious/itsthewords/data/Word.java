package is.justcurious.itsthewords.data;
/**
 * Entity for the representation of a Word containing both 
 * languages and a counter for correct and incorrect guesses.
 * 
 * @author max
 *
 */
public class Word {
	
	// THE ID
	private long id;
	
	// WORD IN LANGUAGE ONE
	private String lang1;
	// WORD IN LANGUAGE TWO
	private String lang2;
	
	// CATEGORY (linked entity?)
	private String category;
	
	// NUMBER OF CORRECT GUESSES
	private int countCorrect;
	// NUMBER OF INCORRECT GUESSES
	private int countIncorrect;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLang1() {
		return lang1;
	}

	public void setLang1(String lang1) {
		this.lang1 = lang1;
	}

	public String getLang2() {
		return lang2;
	}

	public void setLang2(String lang2) {
		this.lang2 = lang2;
	}

	public int getCountCorrect() {
		return countCorrect;
	}
	
	public void setCountCorrect(int count) {
		this.countCorrect = count;
	}

	public void addCorrect() {
		this.countCorrect++;
	}

	public int getCountIncorrect() {
		return countIncorrect;
	}
	
	public void setCountIncorrect(int count) {
		this.countIncorrect = count;
	}

	public void addIncorrect() {
		this.countIncorrect++;
	}

	public int getCountOverall() {
		return (this.countCorrect + this.countIncorrect);
	}

	public String toString(){
		return this.lang1;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
