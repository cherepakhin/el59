package ru.perm.v.el59.office.util;

import org.apache.el.parser.ParseException;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import ru.el59.office.db.Tovar;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.lucene.search.TopScoreDocCollector.create;

public class LuceneSearcher implements ILuceneSearcher {

	// Каталог для индекса
	private String luceneIndexDir = "";
	// Максимальное кол-во результатов
	private int maxCountResult = 10;
	// Коэф-т соответствия
	private Double minLimit = 2.0;

	private static IndexSearcher searcher;
	private static boolean isOpenSearcher = false;

	@Override
	public void addListTovar(List<Tovar> listTovar) throws IOException {
		IndexWriter w = getIndexWriter();
		int i=0;
		for (Tovar tovar : listTovar) {
			System.out.println(i++);
			String tempName = tovar.getName();
			if (tovar.getBrand() != null && !tovar.getBrand().isEmpty()) {
				int pos = tovar.getName().indexOf(tovar.getBrand());
				if (pos >= 0) {
					tempName = tovar.getName().substring(pos);
				}
			}
			w.addDocument(getDocument(tovar.getNnum(), tempName));
		}
		w.close();
	}

	private Document getDocument(Integer nnum, String nameTovar) {
		Document doc = new Document();
		doc.add(new Field("index", getIndex(nameTovar), TextField.TYPE_STORED));
		doc.add(new Field("nnum", nnum.toString(), TextField.TYPE_STORED));
		return doc;
	}

	private IndexWriter getIndexWriter() throws IOException {
		if (isOpenSearcher) {
			closeSearcher();
		}
		RussianAnalyzer analyzer = new RussianAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		Directory directory = FSDirectory.open(Paths.get(getLuceneIndexDir()));
		IndexWriter w = new IndexWriter(directory, config);
		return w;
	}

	private static String getIndex(String s) {
		s = s.replaceAll("(\\d{2,10})", " $1 ");
		s = s.replaceAll("[\"\'\\*\\[\\],.!?;:()\\-\\+/\\{\\}\\\\]", " ");
		s = s.replaceAll(" {2,}", " ");
		return s;
	}

	@Override
	public void addTovar(Tovar tovar) throws IOException {
		IndexWriter w = getIndexWriter();
		String tempName = tovar.getName();
		if (tovar.getBrand() != null && !tovar.getBrand().isEmpty()) {
			int pos = tovar.getName().indexOf(tovar.getBrand());
			if (pos >= 0) {
				tempName = tovar.getName().substring(pos);
			}
		}
		w.addDocument(getDocument(tovar.getNnum(), tempName));
		w.close();
	}

	private void closeSearcher() throws IOException {
		searcher.getIndexReader().close();
		isOpenSearcher = false;
	}

	private IndexSearcher getSearcher() throws IOException {
		if (!isOpenSearcher) {
			Directory directory = FSDirectory.open(Paths
					.get(getLuceneIndexDir()));
			IndexReader reader = DirectoryReader.open(directory);
			searcher = new IndexSearcher(reader);
			isOpenSearcher = true;
		}
		return searcher;
	}

	private QueryParser getQueryParser() throws IOException {
		RussianAnalyzer analyzer = new RussianAnalyzer();
		QueryParser queryParser = new QueryParser("index", analyzer);
		return queryParser;
	}

	@Override
	public List<Integer> getAnalog(String searchTovar) throws IOException,
			ParseException {
		QueryParser queryParser = getQueryParser();
		IndexSearcher _searcher = getSearcher();
        List<Integer> ret = null;
        try {
            ret = getResults(queryParser, _searcher, searchTovar);
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            throw new RuntimeException(e);
        }
        return ret;
	}

	private List<Integer> getResults(QueryParser queryParser,
									 IndexSearcher _searcher, String searchTovar) throws ParseException,
			IOException, org.apache.lucene.queryparser.classic.ParseException {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		Query q = queryParser.parse(getIndex(searchTovar));
		TopScoreDocCollector collector = create(getMaxCountResult(),10);

		_searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = _searcher.doc(docId);
			if (hits[i].score > getMinLimit()) {
				ret.add(Integer.parseInt(d.get("nnum")));
				System.out.println(String.format("Reiting %.10f %s",
						hits[i].score, d.getField("index").stringValue()));
			}
		}
		return ret;
	}

	@Override
	public Map<String, List<Integer>> getAnalogForListName(List<String> listName)
			throws IOException {
		Map<String, List<Integer>> ret = new HashMap<String, List<Integer>>();
		IndexSearcher _searcher = getSearcher();
		QueryParser queryParser = getQueryParser();
		for (String searchTovar : listName) {
            List<Integer> nnums = null;
            try {
                nnums = getResults(queryParser, _searcher,
                        searchTovar);
            } catch (ParseException | org.apache.lucene.queryparser.classic.ParseException e) {
                throw new RuntimeException(e);
            }
            ret.put(searchTovar, nnums);
		}
		return ret;
	}

	public String getLuceneIndexDir() {
		return luceneIndexDir;
	}

	public void setLuceneIndexDir(String luceneIndexDir) {
		this.luceneIndexDir = luceneIndexDir;
	}

	public Double getMinLimit() {
		return minLimit;
	}

	public void setMinLimit(Double minLimit) {
		this.minLimit = minLimit;
	}

	public int getMaxCountResult() {
		return maxCountResult;
	}

	public void setMaxCountResult(int maxCountResult) {
		this.maxCountResult = maxCountResult;
	}

}
