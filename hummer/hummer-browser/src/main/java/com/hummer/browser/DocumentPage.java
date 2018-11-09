package com.hummer.browser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DocumentPage implements Page{
	
	private Document doc;
	private String html;
	private String title;
	private String keywords;
	private String description;
	private String baseURI;
	private String domain;  
	
	@Override
	public void update(String pageSource,String baseUri) {
		this.html = pageSource;
		this.baseURI = baseUri;
		doc = Jsoup.parse(pageSource, baseUri);
		this.title = doc.title();
		Elements keys = doc.head().select("meta[name=keywords]");
		StringBuilder builder = new StringBuilder();
		for(Element keyEl : keys) {
			String value = keyEl.attr("content");
			if(value != null && value.trim().length()>0) {
				builder.append(value);
				builder.append("\t");
			}
		}
		keywords = builder.toString();
		builder = new StringBuilder();
		Elements des = doc.head().select("meta[name=description]");
		for(Element keyEl : des) {
			String value = keyEl.attr("content");
			if(value != null && value.trim().length()>0) {
				builder.append(value);
				builder.append("\t");
			}
		}
		description = builder.toString();
	}

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
	public String html() {
		return html;
	}

	@Override
	public String domain() {
		return baseURI;
	}

	@Override
	public String baseURI() {
		return baseURI;
	}

	@Override
	public String title() {
		return title;
	}

	@Override
	public String keywords() {
		return keywords;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String getBaseURI() {
		return baseURI;
	}

//	@Override
//	public String favorite() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public boolean exist(String selector) {
		Element el = doc.selectFirst(selector);
		return (el != null);
	}

	@Override
	public String text(String selector) {
		Element el = doc.selectFirst(selector);
		if(el != null)
			return el.text();
		return null;
	}

	@Override
	public String[] texts(String selector) {
		Elements els = doc.select(selector);
		if(els.size() > 0) {
			String[] texts = new String[els.size()];
			for(int i=0;i<els.size();i++) {
				texts[i] = els.get(i).text();
			}
			return texts;
		}
		return null;
	}

	@Override
	public String text(String baseSelector, String childSelector) {
		Element parent = doc.selectFirst(baseSelector);
		if(parent != null) {
			Element child = parent.selectFirst(childSelector);
			if(child != null)
				return child.text();
			return null;
		}
		return null;
	}

	@Override
	public String link(String selector) {
		Element el = doc.selectFirst(selector);
		if(el != null) {
			if(el.hasAttr("src")){
				return el.absUrl("src");
			}else if(el.hasAttr("href")) {
				return el.absUrl("href");
			}else {
				return null;
			}
		}
		return null;
	}

	@Override
	public String[] links(String selector) {
		Elements els = doc.select(selector);
		if(els != null) {
			String[] links = new String[els.size()];
			for(int i=0;i<els.size();i++) {
				Element el = els.get(i);
				String link = "";
				if(el.hasAttr("src")){
					link = el.absUrl("src");
				}else if(el.hasAttr("href")) {
					link = el.absUrl("href");
				}
				links[i] = link;
			}
			return links;
		}
		return null;
	}

	@Override
	public String link(String baseSelector, String childSelector) {
		return null;
	}

	@Override
	public String attribute(String selector, String name) {
		Element el = doc.selectFirst(selector);
		if(el != null && el.hasAttr(name)) {
			return el.attr(name);
		}
		return null;
	}

	@Override
	public String[] attributes(String selector, String name) {
		Elements els = doc.select(selector);
		if(els.size() > 0) {
			String[] values = new String[els.size()];
			for(int i=0;i<els.size();i++) {
				Element el = els.get(i);
				if(el.hasAttr(name)) {
					values[i] = el.attr(name);
				}else {
					values[i] = el.attr("");
				}
			}
			return values;
		}
		return null;
	}

	@Override
	public String attribute(String baseSelector, String childSelector, String name) {
		
		return null;
	}

}
