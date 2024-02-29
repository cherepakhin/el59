package ru.perm.v.el59.office.parser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.iproviders.subs.IMainFeatureProvider;
import ru.perm.v.el59.office.iproviders.web.IParserSite;
import ru.perm.v.el59.office.iproviders.web.ISubsFeatureProvider;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AParserSite implements IParserSite {
    // Кодировка сайта
    private ITovarInfoProvider tovarInfoProvider;
    private ITovarProvider tovarProvider;
    private ISubsFeatureProvider subsFeatureProvider;
    private IMainFeatureProvider mainFeatureProvider;
    private Document doc = null;
    private String url;
    protected TovarInfo tovarInfo;
    private Integer nnum;
    protected Manager manager;
    private static DimensionRecognizer dimensionRecognizer = new DimensionRecognizer();

    /**
     * Формирование информации о товаре с сайта
     *
     * @param nnum - н.номер
     * @return - заполненный данными с сайта объект TovarInfo
     * @throws Exception
     */
    @Override
    public Tovar parseTovar(Integer nnum, String _url, Manager manager,
                            Boolean parseProperty, Boolean parsePicture)
            throws XPathExpressionException, IOException, Exception {
        Tovar tovar = setNnumUrl(nnum, _url, parseProperty, parsePicture);
        if (tovar == null)
            return null;
        if (parseProperty) {
            tovarInfo = fillInfo();
        }
        if (parsePicture) {
            tovarInfo = fillPhoto();
        }
        this.manager = manager;
        if (getUrl().startsWith("http")) {
            tovarInfo.setLink(getUrl());
        }
        tovarInfo.setManager(manager);
        setOld(tovarInfo);
        getTovarInfoProvider().update(tovarInfo);
        // getSubsFeatureProvider().processTovarInfo(tovarInfo);
        getMainFeatureProvider().substituteMainFeature(tovarInfo);
        return tovarInfo.getTovar();
    }

    protected Tovar setNnumUrl(Integer nnum, String _url,
                               Boolean parseProperty, Boolean parsePicture) throws Exception {
        if (_url.length() < 40) {
            Logger.getLogger(this.getClass()).info(
                    "Парсинг " + nnum + " " + _url);
        } else {
            Logger.getLogger(this.getClass()).info("Парсинг " + nnum);
        }
        setUrl(_url);
        setNnum(nnum);
        if (getTovarInfo() != null) {
            clearFeaturesPhoto(getTovarInfo(), parseProperty, parsePicture);
            if (_url.startsWith("http")) {
                getTovarInfo().setLink(_url);
            }
            return getTovarInfo().getTovar();
        } else {
            tovarInfo = new TovarInfo();
            Tovar tovar = getTovarProvider().read(nnum);
            tovarInfo.setTovar(tovar);
            if (_url.startsWith("http")) {
                tovarInfo.setLink(_url);
            } else {
                tovarInfo.setLink("");
            }
            getTovarInfoProvider().create(tovarInfo);
            setTovarInfo(tovarInfo);
            return tovarInfo.getTovar();
        }
    }

    private void clearFeaturesPhoto(TovarInfo ti, Boolean parseProperty,
                                    Boolean parsePicture) throws Exception {
        if (parseProperty) {
            ti.getListFeature().clear();
        }
        if (parsePicture) {
            ti.getListPhoto().clear();
        }
        getTovarInfoProvider().update(ti);
    }

    /**
     * Загрузка картинки в локальный каталог
     *
     * @param link      - url картинки
     * @param outputDir - каталог для сохранения
     * @return - полный путь файла с картинкой
     * @throws Exception
     */
    protected String loadPhoto(String link, Integer nnum, String delimeter,
                               String endDelimeter, int i) throws Exception {
        String ret = null;
        int pos = 0;
        if (delimeter != null) {
            pos = link.lastIndexOf(delimeter);
            if (pos == -1) {
                return "";
            }
        }
        // На случай если расширение не найдено. По умолчанию пускай будет jpg
        String ext = ".jpg";
        pos = link.lastIndexOf(".");
        if (pos > 0) {
            if (endDelimeter != null) {
                int endpos = link.lastIndexOf(endDelimeter);
                ext = link.substring(pos, endpos);
            } else {
                ext = link.substring(pos);
            }
        }
        ret = getFullFileName(getDirForPhoto(nnum), nnum + "_" + i + ext);
        if (link.length() < 40) {
            Logger.getLogger(this.getClass()).info(
                    String.format("%s %d %s", link, i, ret));
        }
        try {
            FileUtils.copyURLToFile(new URL(link), new File(ret));
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).severe(e.getMessage());
            return null;
        }
        // FileUtils.copyURLToFile(new URL(link),new
        // File("/home/vasi/"+nnum+"_"+i+ext));
        return ret;
    }

    protected Document getDoc(String url) throws IOException {

        /*
         * Tidy tidy = new Tidy(); tidy.setQuiet(true); tidy.setXHTML(true);
         * tidy.setInputEncoding(getCHARSET_SITE()); URL _url = new URL(url);
         * Document _doc = null; URLConnection hc = _url.openConnection();
         *
         * hc.setRequestProperty( "User-Agent",
         * "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:22.0) Gecko/20100101 Firefox/22.0 YB/7.6.0"
         * );
         */
        // InputStream input = hc.getInputStream();
        // FileUtils.copyInputStreamToFile(input, new
        // File("/home/vasi/temp/hc.txt"));

        // _doc = tidy.parseDOM(hc.getInputStream(), null);

        InputStream in = null;
//        HtmlCleaner cleaner = new HtmlCleaner();
//        TagNode node = null;
//        if (url.startsWith("http")) {
//            // Если в url действительно url
//            System.setProperty("http.agent",
//                    "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0");
//            System.setProperty("User-Agent",
//                    "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0");
//            URL _url = new URL(url);
//            // Вывод в файл
//
///*			URLConnection hc = _url.openConnection(); hc.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0"
//			);
//			InputStream input = hc.getInputStream();
//			FileUtils.copyInputStreamToFile(input, new File("/home/vasi/temp/hc.txt"));
//*/
//            // Конец вывода в файл
//            // URL _url = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
//            conn.setRequestProperty("User-Agent",
//                    "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0");
//            conn.setRequestProperty("http.agent",
//                    "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0");
//            // conn.setReadTimeout(1000);
//            conn.setAllowUserInteraction(true);
//            in = conn.getInputStream();
//            node = cleaner.clean(in, getCHARSET_SITE());
//        } else {
//            // В url содержимое html-страницы
//            node = cleaner.clean(url);
//        }
//        // TagNode node = cleaner.clean(in);
//        DomSerializer ser = new DomSerializer(cleaner.getProperties());
        Document _doc = null;
//        try {
//            _doc = ser.createDOM(node);
//            setDoc(_doc);
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//        // InputStream input = new URL(url).openStream();
//        // org.jsoup.nodes.Document jsoupDoc =
//        // Jsoup.parse(input,getCHARSET_SITE(), url);
//        // org.jsoup.nodes.Document jsoupDoc =
//        // Jsoup.connect(url).timeout(0).get();
//        // W3CDom w3cDom = new W3CDom();
//        // org.w3c.dom.Document _doc = w3cDom.fromJsoup(jsoupDoc);

        return _doc;
    }

    protected XPath getXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        return xpath;
    }

    protected static String getFullFileName(String outputDir, String filename) {
        String fullname = FilenameUtils.concat(outputDir, filename);
        // if(!outputDir.endsWith(DELIMETER_DIR))
        // outputDir=outputDir+DELIMETER_DIR;
        // outputDir=outputDir+filename;
        return fullname;
    }

    public ITovarInfoProvider getTovarInfoProvider() {
        return tovarInfoProvider;
    }

    public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
        this.tovarInfoProvider = tovarInfoProvider;
    }

    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }

    public abstract String getCHARSET_SITE();

    public String getDirForPhoto(Integer nnum) throws Exception {
        return getTovarInfoProvider().getDirForPhoto(nnum);
    }

    public String getBaseDirForPhoto() throws Exception {
        return getTovarInfoProvider().getDirForPhoto();
    }

    public ISubsFeatureProvider getSubsFeatureProvider() {
        return subsFeatureProvider;
    }

    public void setSubsFeatureProvider(ISubsFeatureProvider subsFeatureProvider) {
        this.subsFeatureProvider = subsFeatureProvider;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) throws IOException {
        this.url = url;
        Document _doc = getDoc(url);
        setDoc(_doc);
    }

    public TovarInfo getTovarInfo() {
        return tovarInfo;
    }

    public Integer getNnum() {
        return nnum;
    }

    public void setNnum(Integer nnum) {
        this.nnum = nnum;
        TovarInfo _tovarInfo = (TovarInfo) getTovarInfoProvider().initialize(
                nnum);
        if (_tovarInfo == null) {
            _tovarInfo = new TovarInfo();
            Tovar tovar = (Tovar) getTovarProvider().initialize(nnum);
            if (tovar != null) {
                _tovarInfo.setTovar(tovar);
                setTovarInfo(_tovarInfo);
            } else {
                Logger.getLogger(this.getClass()).severe(
                        "Товар не найден " + nnum);
            }
        } else {
            setTovarInfo(_tovarInfo);
        }
    }

    public void setTovarInfo(TovarInfo tovarInfo) {
        this.tovarInfo = tovarInfo;
    }

    public abstract String getSampleLink();

    public IMainFeatureProvider getMainFeatureProvider() {
        return mainFeatureProvider;
    }

    public void setMainFeatureProvider(IMainFeatureProvider mainFeatureProvider) {
        this.mainFeatureProvider = mainFeatureProvider;
    }

    protected void setOld(TovarInfo tovarInfo) {
        for (Feature feature : tovarInfo.getListFeature()) {
            feature.setOldname(feature.getName());
            feature.setOldval(feature.getVal());
        }
    }

    protected List<Feature> recognizeDimension(Feature f) {
//        return dimensionRecognizer.recognize(f);
        return Collections.emptyList();
    }

    /**
     * Очистка от &quot; и подобных
     *
     * @param s
     * @return
     */
    protected String clearHTML(String s) {
        // Pattern p = Pattern.compile("&.*;");
        s = s.replaceAll("&quot;", "\"");
        s = s.replaceAll("&amp;", "&");
        String ret = s.replaceAll("&.*;", " ").replace("null", "")
                .replace("\n", "");
        return ret;
    }

    /**
     * Проверка существования значения(valAttr) в аттрибуте элемена с именем
     * nameAttr на глубину depth, начиная вверх от узла node
     *
     * @param node
     * @param nameAttr
     * @param valAttr
     * @param depth
     * @return
     */
    protected boolean isValInAtributeInParent(Node node, String nameAttr,
                                              String valAttr, int depth) {
        for (int i = 0; i < depth; i++) {
            NamedNodeMap attrName = node.getParentNode().getAttributes();
            Node aNameClass = attrName.getNamedItem(nameAttr);
            if (aNameClass != null
                    && (aNameClass.getNodeValue().contains(valAttr))) {
                return true;
            }
            node = node.getParentNode();
        }
        return false;
    }

    /**
     * Распознавание размеров. Если распознаны размеры, то хар-ка f делится на
     * несколько хар-к и добавляется к текущему tovarInfo
     *
     * @param f - хар-ка
     * @return - были распознаны и добавлены к тек. tovarInfo
     */
    protected boolean isLoadedDimension(Feature f) {
        List<Feature> listFeature = recognizeDimension(f);
        if (listFeature.size() > 0) {
            for (Feature feature : listFeature) {
                if (!tovarInfo.getListFeature().contains(feature)) {
                    tovarInfo.getListFeature().add(feature);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    protected abstract TovarInfo fillInfo() throws Exception;

    protected abstract TovarInfo fillPhoto() throws Exception;
}
