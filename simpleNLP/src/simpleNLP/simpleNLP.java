package simpleNLP;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.jar.JarEntry;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.io.EncodingPrintWriter.out;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.*;

public class simpleNLP {

	public static HashMap<String, Integer> count_word;
	public static int n_ngrame = 3;
	static Writer objwriter = new Writer(
			"D:\\AndroidWorking\\NLP\\fileForreview\\output", "out");
	static HashMap<String, String> allword = new HashMap<String, String>();
	static HashMap<String, String> stopword = new HashMap<String, String>();

	static int state = 0;

	public static void main(String[] args) throws IOException {

		CreateStopword();
		CreateDic();
		CreateInputFile();

		objwriter.CloseWriter();

		System.out.println("END PROGRAME");

	}

	private static void CreateStopword() {
		// TODO Auto-generated method stub
		ReadFileAndFloder objRFAF = new ReadFileAndFloder(
				"D:\\AndroidWorking\\NLP\\fileForreview\\stopword") {

			@Override
			public void GetString(String input) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ReadLine(String input) {
				// TODO Auto-generated method stub
				stopword.put(input, "1");
			}

			@Override
			public void ReadFile(String input) {
				// TODO Auto-generated method stub

			}

		};
	}

	private static void CreateInputFile() {
		// TODO Auto-generated method stub
		ReadFileAndFloder objRFAF = new ReadFileAndFloder(
				"D:\\AndroidWorking\\NLP\\fileForreview\\input") {

			@Override
			public void GetString(String input) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ReadLine(String input) {
				// TODO Auto-generated method stub
				if (input.split("#").length > 1) {
					ArrayList<Integer> word = GetNLP(input.split("#")[1]);
					String write = "";

					Collections.sort(word);

					write += input.split("#")[0] + " ";
					int ptn = 0;
					for (int i = 0; i < allword.size(); i++) {
						if (ptn != word.size() && i == word.get(ptn)) {
							write += String.valueOf(i + 1) + ":1 ";
							ptn += 1;
						} else {
							write += String.valueOf(i + 1) + ":0 ";
						}
					}

					objwriter.WriteLine(write);
				}
			}

			@Override
			public void ReadFile(String input) {
				// TODO Auto-generated method stub

			}

		};

		objRFAF.StartRead();

	}

	private static void CreateDic() {
		// TODO Auto-generated method stub

		ReadFileAndFloder objRFAF = new ReadFileAndFloder(
				"D:\\AndroidWorking\\NLP\\fileForreview\\dic") {

			@Override
			public void GetString(String input) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ReadLine(String input) {
				// TODO Auto-generated method stub
				addWord(input);
			}

			@Override
			public void ReadFile(String input) {
				// TODO Auto-generated method stub

			}
		};
		objRFAF.StartRead();

	}

	public static ArrayList<Integer> GetNLP(String input) {

		ArrayList<Integer> output = new ArrayList<>();

		Properties props = new Properties();
		props.put("annotators", "tokenize,ssplit,pos,lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(input);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			// ReadNGram(sentence);

			int stateNOT = 0;
//			System.out.println("sentent:" + sentence.toString());
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// String word = token.get(TextAnnotation.class);
				// String pos = token.get(PartOfSpeechAnnotation.class);
				// String ne = token.get(NamedEntityTagAnnotation.class);
				String lemma = token.getString(LemmaAnnotation.class);

				if (lemma.equals("not")) {
					if (stateNOT == 0) {
						stateNOT = 1;
					} else {
						stateNOT = 0;
					}
				}

				if (stateNOT == 1)
					lemma += "-not";

				if (allword.get(lemma) != null) {
					output.add(Integer.parseInt(allword.get(lemma)));
				}
			}

			// Tree tree = sentence.get(TreeAnnotation.class);
			// SemanticGraph dependencies = sentence
			// .get(CollapsedCCProcessedDependenciesAnnotation.class);
		}

		return output;

		// Map<Integer, CorefChain> graph = document
		// .get(CorefChainAnnotation.class);
	}

	private static void addWord(String lemma) {
		// TODO Auto-generated method stub
		if (stopword.get(lemma) == null) {
			allword.put(lemma.split("#")[0], lemma.split("#")[1]);
			allword.put(lemma.split("#")[0] + "-not", lemma.split("#")[1]
					+ allword.size());
		}
	}

	// public static void CountWord(String word) {
	//
	// // System.out.println("get word:" + word);
	// if (count_word.get(word) == null)
	// count_word.put(word, 0);
	// count_word.put(word, count_word.get(word) + 1);
	// }

}
