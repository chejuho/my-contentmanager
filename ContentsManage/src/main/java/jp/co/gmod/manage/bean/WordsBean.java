package jp.co.gmod.manage.bean;

import java.util.HashMap;
import java.util.Map;

public class WordsBean {

	private Map<String, WordBean> words = new HashMap<String, WordBean>();

	public void addWord(WordBean word) {
		words.put(word.getId(), word);
	}

	public Map<String, WordBean> getWords() {
		return words;
	}

	public WordBean getWord(String key) {
		return words.get(key);
	}
}
